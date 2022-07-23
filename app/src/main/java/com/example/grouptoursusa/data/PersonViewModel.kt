package com.example.grouptoursusa.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(application: Application) : AndroidViewModel(application) {

    val allPeople: LiveData<List<Person>>
    private val repository: PersonRepository

    init {
        val personDao = PersonDatabase.getDatabase(application).personDao()
        repository = PersonRepository(personDao)
        allPeople = repository.allPeople
    }

    fun addPerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPerson(person)
        }
    }

    fun checkIn(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkIn(person)
        }
    }

    fun updatePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePerson(person)
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePerson(person)
        }
    }
}