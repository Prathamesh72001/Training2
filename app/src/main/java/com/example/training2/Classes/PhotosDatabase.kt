package com.example.training2.Classes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.training2.Interfaces.Photos_DAO

@Database(entities = [Photos::class],version = 4)
abstract class PhotosDatabase : RoomDatabase(){
    abstract fun photoDao(): Photos_DAO

    companion object {
        private var instance: PhotosDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PhotosDatabase {
            if(instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,PhotosDatabase::class.java,"mydb").fallbackToDestructiveMigration().build()
            }

            return instance!!

        }
    }
}