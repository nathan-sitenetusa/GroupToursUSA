package com.example.grouptoursusa.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPerson(person: Person)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun checkIn(person: Person)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updatePerson(person: Person)

    @Query( "SELECT * FROM person_table ORDER BY id ASC" )
    fun getAllPeople(): LiveData<List<Person>>

    @Delete
    suspend fun deletePerson(person: Person)

    @Query( "SELECT * FROM person_table WHERE id = :personId")
    fun findPerson(personId : Int) : Person

    @Query( "DELETE FROM person_table")
    fun resetDatabase()
}