package com.example.grouptoursusa.data

import androidx.room.*

@Entity(tableName = "person_table")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val number: Long,
    var checkedIn: Boolean) {
}