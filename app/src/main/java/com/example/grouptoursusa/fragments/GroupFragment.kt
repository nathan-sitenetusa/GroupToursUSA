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
        return view
    }

    private fun addContact(view: View) {
        val name = view.findViewById<EditText>(R.id.editTextPersonName).text.toString()
        val phone = view.findViewById<EditText>(R.id.editTextPhone).text.toString()

        if (inputCheck(name, phone)) {
            val person = Person(name, Integer.parseInt(phone))
            mPersonViewModel.addPerson(person)
            Toast.makeText(requireContext(), "Contact added", Toast.LENGTH_SHORT).show()

            view.findViewById<EditText>(R.id.editTextPersonName).text.clear()
            view.findViewById<EditText>(R.id.editTextPhone).text.clear()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(name: String, phoneNumber: String): Boolean {
        return !(TextUtils.isEmpty(name) || phoneNumber.isEmpty())
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GroupFragment().apply {
            }
    }
}