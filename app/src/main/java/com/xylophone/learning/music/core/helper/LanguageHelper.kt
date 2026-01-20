package com.xylophone.learning.music.core.helper

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build

import java.util.Locale

object LanguageHelper {
    private var myLocale: Locale? = null

    fun setLocale(context: Context) {
        val language = SharePreferenceHelper(context).getPreLanguage()
        if (language.isEmpty()) {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        } else {
            changeLang(language, context)
        }
    }

    fun changeLang(lang: String, context: Context) {
        if (lang.equals("", ignoreCase = true)) return
        myLocale = Locale(lang)
        saveLocale(context, lang)
        Locale.setDefault(myLocale!!)
        val config = Configuration()
        config.setLocale(myLocale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun saveLocale(context: Context, lang: String) {
        SharePreferenceHelper(context).setPreLanguage(lang)
    }

    /**
     * Wrap context với locale mới để đảm bảo getString() luôn trả về đúng ngôn ngữ
     * Method này được gọi trong attachBaseContext() của Activity/Dialog
     */
    fun wrapContext(context: Context): Context {
        val language = SharePreferenceHelper(context).getPreLanguage()
        if (language.isEmpty()) {
            return context
        }

        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }
}