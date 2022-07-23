package com.example.grouptoursusa.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grouptoursusa.R
import com.example.grouptoursusa.adapters.ContactAdapter
import com.example.grouptoursusa.data.Person
import com.example.grouptoursusa.data.PersonViewModel

class ContactFragment : Fragment() {

    private lateinit var mPersonViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        mPersonViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        val saveButton = view.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            updatePerson()
        }

        val deleteButton = view.findViewById<Button>(R.id.deleteButton)

        deleteButton.setOnClickListener {
            deletePerson()
        }

        return view
    }

    private fun updatePerson() {
        val personName = view?.findViewById<EditText>(R.id.editPersonName)?.text.toString()
        val personPhone = view?.findViewById<EditText>(R.id.editPersonPhone)?.text.toString()
        val personNotes = view?.findViewById<EditText>(R.id.editPersonNotes)?.text.toString()

        // if input check comes back bad, throw error
        // update checkedIn to grab current value
        if (inputCheck(personName, personPhone)) {
            val person = Person(0, personName, personPhone.toLong(), personNotes, false)
            mPersonViewModel.updatePerson(person)
            Toast.makeText(requireContext(), "Person Updated", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_contactFragment_to_groupFragment)
        }
        // bad input
        else {
            Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
        }
    }

    // check inputs
    // don't need to check if notes are empty, notes can be anything
    private fun inputCheck(personName: String, personPhone: String): Boolean {
        return !(TextUtils.isEmpty(personName) && TextUtils.isEmpty(personPhone))
    }

    private fun deletePerson() {
        //TODO: Delete Person
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
            }
    }
}