package com.example.grouptoursusa.data

import androidx.room.*

@Entity(tableName = "person_table")
data class Person (
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val number: Int) {
}