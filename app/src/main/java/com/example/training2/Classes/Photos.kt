package com.example.training2.Classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Flicker_Table")
class Photos(@ColumnInfo(name = "id")val id:String,
             @ColumnInfo(name = "owner")val owner:String,
             @ColumnInfo(name = "secret")val secret:String,
             @ColumnInfo(name = "server")val server:String,
             @ColumnInfo(name = "farm")val farm:Int,
             @ColumnInfo(name = "title")val title:String,
             @ColumnInfo(name = "isfriend")val isfriend:Int,
             @ColumnInfo(name = "ispublic")val ispublic:Int,
             @ColumnInfo(name = "isfamily")val isfamily:Int,
             @PrimaryKey(autoGenerate =true)val uid:Int?=null) {
}