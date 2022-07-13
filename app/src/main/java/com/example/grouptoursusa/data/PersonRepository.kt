package com.example.grouptoursusa.data

import androidx.lifecycle.LiveData

class PersonRepository(private val personDAO: PersonDAO) {

    val allPeople: LiveData<List<Person>> = personDAO.getAllPeople()

    suspend fun addPerson(person: Person) {
        personDAO.addPerson(person)
    }
}