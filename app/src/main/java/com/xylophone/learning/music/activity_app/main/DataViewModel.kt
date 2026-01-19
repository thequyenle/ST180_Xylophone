package com.xylophone.learning.music.activity_app.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xylophone.learning.music.core.helper.AssetHelper
import com.xylophone.learning.music.core.helper.MediaHelper
import com.xylophone.learning.music.core.utils.key.ValueKey
import com.xylophone.learning.music.data.model.PartAPI
import com.xylophone.learning.music.data.model.custom.CustomizeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel() : ViewModel() {
    private val _allData = MutableStateFlow<ArrayList<CustomizeModel>>(arrayListOf())
    val allData: StateFlow<ArrayList<CustomizeModel>> = _allData.asStateFlow()
    private val _getDataAPI = MutableLiveData<List<PartAPI>>()
    val getDataAPI: LiveData<List<PartAPI>> get() = _getDataAPI

    // Track if data loading has completed (success or fail)
    private var dataLoaded = false

    fun saveAndReadData(context: Context) {
        viewModelScope.launch {
            val timeStart = System.currentTimeMillis()
            val list = withContext(Dispatchers.IO) {
                // Lần đầu vào app -> Load data Asset -> Lưu file internal
                if (!MediaHelper.checkFileInternal(context, ValueKey.DATA_FILE_INTERNAL)) {
                    AssetHelper.getDataFromAsset(context)
                }

                val totalData = MediaHelper.readListFromFile<CustomizeModel>(context, ValueKey.DATA_FILE_INTERNAL)
                    .toCollection(ArrayList())
                val dataApi = MediaHelper.readListFromFile<CustomizeModel>(context, ValueKey.DATA_FILE_API_INTERNAL)
                // Không gọi API nữa, chỉ dùng data đã cache
                totalData.addAll(dataApi)
                totalData
            }
            _allData.value = list
            dataLoaded = true // Mark data as loaded
            val timeEnd = System.currentTimeMillis()
            Log.d("nbhieu", "time load data: ${timeEnd - timeStart}")
            Log.d("nbhieu", "data loaded: ${list.size} items")
        }
    }

    fun ensureData(context: Context) {
        if (_allData.value.isEmpty() && !dataLoaded) {
            saveAndReadData(context)
        }
    }

    fun isDataLoaded(): Boolean = dataLoaded
}