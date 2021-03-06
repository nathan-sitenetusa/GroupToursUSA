package com.example.grouptoursusa.data

import androidx.lifecycle.LiveData

class PersonRepository(private val personDAO: PersonDAO) {

    val allPeople: LiveData<List<Person>> = personDAO.getAllPeople()

    suspend fun addPerson(person: Person) {
        personDAO.addPerson(person)
    }

    suspend fun checkIn(person: Person) {
        personDAO.checkIn(person)
    }

    suspend fun updatePerson(person: Person) {
        personDAO.updatePerson(person)
    }

    suspend fun deletePerson(person: Person) {
        personDAO.deletePerson(person)
    }

    fun findPerson(personId : Int) : Person {
        return personDAO.findPerson(personId)
    }

    fun resetDatabase() {
        personDAO.resetDatabase()
    }
}