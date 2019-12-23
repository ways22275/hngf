package com.example.sideproject.data.local

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sideproject.data.local.User.UserDao
import com.example.sideproject.data.model.UserInfo

@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class LotteryDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var InstanceRoom : LotteryDatabase? = null

        fun getDatabase(application : Application) : LotteryDatabase =
            InstanceRoom ?: synchronized(this) {
                InstanceRoom ?: providerDatabase(application).also { InstanceRoom = it }
            }

        private fun providerDatabase(context: Context): LotteryDatabase {
            return Room
                .databaseBuilder<LotteryDatabase>(context.applicationContext, LotteryDatabase::class.java, "Lottery.db")
                .build()
        }
    }

    abstract fun userModel() : UserDao
}