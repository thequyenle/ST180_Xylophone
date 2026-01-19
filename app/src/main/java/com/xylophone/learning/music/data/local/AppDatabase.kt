package com.xylophone.learning.music.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xylophone.learning.music.data.local.dao.UserDao
import com.xylophone.learning.music.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}