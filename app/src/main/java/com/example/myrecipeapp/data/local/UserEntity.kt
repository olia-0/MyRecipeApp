//package com.example.myrecipeapp.data.local
//
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "user")
//data class UserEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    @ColumnInfo(name = "first_name")
//    val firstName: String,
//    @ColumnInfo(name = "second_name", defaultValue = "null")
//    val secondName: String?,
//    @ColumnInfo(name = "password")
//    val password: String,
//    @ColumnInfo(name = "email", defaultValue = "null")
//    val email: String?,
//    @ColumnInfo(name = "phone", defaultValue = "null")
//    val phone: String?,
//    @ColumnInfo(name = "country", defaultValue = "null")
//    val country: String?,
//    @ColumnInfo(name = "photo", defaultValue = "null")
//    val photo: String?
//)