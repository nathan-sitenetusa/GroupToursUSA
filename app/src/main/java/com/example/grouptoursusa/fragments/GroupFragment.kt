package com.example.grouptoursusa.fragments

import android.os.Bundle
import android.telephony.PhoneNumberUtils
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grouptoursusa.R
import com.example.grouptoursusa.adapters.ContactAdapter
import com.example.grouptoursusa.data.Person
import com.example.grouptoursusa.data.PersonViewModel

class GroupFragment : Fragment() {

    private lateinit var mPersonViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group, container, false)

        //Check-in button
        val checkInBtn = view.findViewById<Button>(R.id.checkInButton)
        checkInBtn.setOnClickListener() {

        }

        //RecyclerView
        val adapter = ContactAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mPersonViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        mPersonViewModel.allPeople.observe(viewLifecycleOwner, Observer { person ->
            adapter.setData(person)
        })

        val addBtn = view.findViewById<Button>(R.id.addButton)

        addBtn.setOnClickListener {
            addContact(view)
        }

        val clearBtn = view.findViewById<Button>(R.id.clearListButton)
        clearBtn.setOnClickListener() {
            for(people in listOf(mPersonViewModel.allPeople.value)) {
                if (people != null) {
                    for(person in people) {
                        checkInPerson(person, false)
//                        Toast.makeText(requireContext(), "Person: " + person.name, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view
    }

    private fun addContact(view: View) {
        val name = view.findViewById<EditText>(R.id.editTextPersonName).text.toString()
        val phone = view.findViewById<EditText>(R.id.editTextPhone).text.toString()

        if (inputCheck(name, phone)) {
            val person = Person(0, name, phone.toLong(), false)
            mPersonViewModel.addPerson(person)
            Toast.makeText(requireContext(), String.format("Contact %s added", name), Toast.LENGTH_SHORT).show()
            view.findViewById<EditText>(R.id.editTextPersonName).text.clear()
            view.findViewById<EditText>(R.id.editTextPhone).text.clear()
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
        // if phone doesn't match, return false
        if (!phoneMatch)
            return false
        // validation true
        return true
    }

    private fun checkPhone(phone: String): Boolean {
        var re = Regex("[0-9]{10,11}+")
        return phone.matches(re)
    }

    private fun checkInPerson(person : Person, checked : Boolean) {
        person.checkedIn = checked
        mPersonViewModel.checkIn(person)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GroupFragment().apply {
            }
    }
}