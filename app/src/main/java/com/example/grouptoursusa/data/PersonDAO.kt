package com.example.grouptoursusa.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPerson(person: Person)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun checkIn(person: Person)

    @Query( "SELECT * FROM person_table ORDER BY name ASC" )
    fun getAllPeople(): LiveData<List<Person>>
}