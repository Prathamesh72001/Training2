package com.example.training2.Interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.training2.Classes.Photos

@Dao
interface Photos_DAO {
    @Insert
    fun insert(photo:Photos)

    @Query("Delete from Flicker_Table where id=:ID")
    fun delete(ID:String)

    @Query("Select * from Flicker_Table")
    fun getFavPhotos(): LiveData<MutableList<Photos>>

    @Query("Select exists(Select * from Flicker_Table where secret=:Secret)")
    fun search(Secret:String):Boolean
}