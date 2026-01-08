package com.xylophone.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xylophone.data.local.dao.UserDao
import com.xylophone.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}