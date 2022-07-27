package com.example.grouptoursusa.fragments

import android.app.AlertDialog
import android.content.DialogInterface
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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grouptoursusa.R
import com.example.grouptoursusa.adapters.GroupAdapter
import com.example.grouptoursusa.data.Person
import com.example.grouptoursusa.data.PersonViewModel

class GroupFragment : Fragment() {

    private val mPersonViewModel: PersonViewModel by activityViewModels()

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
        val resetBtn = view.findViewById<Button>(R.id.resetButton)
        resetBtn.setOnClickListener() {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(R.string.confirm_clear_database)
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { _,_ ->
                        resetDatabase()
                    })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { _,_ ->
                    })
            builder.create()
            builder.show()
        }

        //RecyclerView
        val adapter = GroupAdapter(findNavController())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mPersonViewModel.allPeople.observe(viewLifecycleOwner, Observer { person ->
            adapter.setData(person)
        })

        val addBtn = view.findViewById<Button>(R.id.addButton)

        addBtn.setOnClickListener {
            addContact(view)
        }

        val clearBtn = view.findViewById<Button>(R.id.clearListButton)
        clearBtn.setOnClickListener() {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(R.string.confirm_reset)
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { _,_ ->
                        for(people in listOf(mPersonViewModel.allPeople.value)) {
                            if (people != null) {
                                for(person in people) {
                                    checkInPerson(person)
                                }
                            }
                        }
                    })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { _,_ ->
                    })
            builder.create()
            builder.show()
        }
        return view
    }

    private fun addContact(view: View) {
        val name = view.findViewById<EditText>(R.id.editTextPersonName).text.toString()
        val phone = view.findViewById<EditText>(R.id.editTextPhone).text.toString()

        if (inputCheck(name, phone)) {
            val person = Person(0, name, phone.toLong(), null, false)
            mPersonViewModel.addPerson(person)
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

    // currently, only check for 10 digits, not handling +1 or any other codes
    private fun checkPhone(phone: String): Boolean {
        val re = Regex("[0-9]{10}+")
        return phone.matches(re)
    }

    private fun checkInPerson(person : Person) {
        person.checkedIn = false
        mPersonViewModel.checkIn(person)
    }

    private fun resetDatabase() {
        mPersonViewModel.resetDatabase()
    }
}