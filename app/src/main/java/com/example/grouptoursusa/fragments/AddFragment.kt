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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.grouptoursusa.R
import com.example.grouptoursusa.data.Person
import com.example.grouptoursusa.data.PersonViewModel

class AddFragment : Fragment() {

    private val mPersonViewModel: PersonViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            addContact(view)
        }
        return view
    }

    private fun addContact(view: View) {
        val name = view.findViewById<EditText>(R.id.editTextPersonName).text.toString()
        val phone = view.findViewById<EditText>(R.id.editTextPhone).text.toString()
        val notes = view.findViewById<EditText>(R.id.editPersonNotes).text.toString()
        if (inputCheck(name, phone)) {
            val person = Person(0, name, phone.toLong(), notes, false)
            mPersonViewModel.addPerson(person)
            // navigate back to group listing
            findNavController().navigate(R.id.groupFragment)
        } else {
            Toast.makeText(requireContext(), "Contact Not Added, please check all fields are valid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String, phoneNumber: String): Boolean {
        val nameEmpty: Boolean = TextUtils.isEmpty(name)
        val phoneMatch: Boolean = checkPhone(phoneNumber)
        // if name empty, return false
        if (nameEmpty)
            return false
        // if phone isn't valid, return false
        if (!phoneMatch)
            return false
        // validation true
        return true
    }

    private fun checkPhone(phone: String): Boolean {
        val re = Regex("[0-9]{10}+")
        return phone.matches(re)
    }
}