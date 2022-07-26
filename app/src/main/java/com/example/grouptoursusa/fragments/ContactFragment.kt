package com.example.grouptoursusa.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.grouptoursusa.R
import com.example.grouptoursusa.data.Person
import com.example.grouptoursusa.data.PersonViewModel

class ContactFragment : Fragment() {

    private val mPersonViewModel: PersonViewModel by activityViewModels()
    private var currentPersonId: Int = 0
    private var currentPersonCheckedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        //save current person information
        val selectedContactId = navArgs<ContactFragmentArgs>().value.id
        val selectedContactName = navArgs<ContactFragmentArgs>().value.name
        val selectedContactNumber = navArgs<ContactFragmentArgs>().value.number
        val selectedContactNotes = navArgs<ContactFragmentArgs>().value.notes
        val selectedContactCheckedIn = navArgs<ContactFragmentArgs>().value.checkedIn
        val person = Person(selectedContactId, selectedContactName, selectedContactNumber.toLong(), selectedContactNotes, selectedContactCheckedIn)
        currentPersonId = selectedContactId
        currentPersonCheckedIn = selectedContactCheckedIn

        //hook up save button
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            updatePerson()
        }

        //hook up check in button
        val checkInBtn = view.findViewById<Button>(R.id.checkInBtn)
        updateCheckInButton(view, selectedContactCheckedIn)
        checkInBtn.setOnClickListener {
            currentPersonCheckedIn = !currentPersonCheckedIn
            updateCheckInButton(view, currentPersonCheckedIn)
        }

        //hook up delete button
        val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        deletePerson(person)
                    })
                .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id ->
                    // cancel
                })
            builder.create()
            builder.show()
        }

        //set the UI values to the retrieved person
        view.findViewById<TextView>(R.id.editPersonName).text = selectedContactName
        view.findViewById<TextView>(R.id.editPersonPhone).text = selectedContactNumber.toString()
        view.findViewById<TextView>(R.id.editPersonNotes).text = selectedContactNotes

        return view
    }

    private fun updatePerson() {
        val personName = view?.findViewById<EditText>(R.id.editPersonName)?.text.toString()
        val personPhone = view?.findViewById<EditText>(R.id.editPersonPhone)?.text.toString()
        val personNotes = view?.findViewById<EditText>(R.id.editPersonNotes)?.text.toString()

        // if input check comes back bad, throw error
        // update checkedIn to grab current value
        if (inputCheck(personName, personPhone)) {
            val person = Person(currentPersonId, personName, personPhone.toLong(), personNotes, currentPersonCheckedIn)
            mPersonViewModel.updatePerson(person)

            findNavController().navigate(R.id.viewGroup)
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

    private fun deletePerson(person: Person) {
        mPersonViewModel.deletePerson(person)
        findNavController().navigate(R.id.viewGroup)
    }

    private fun updateCheckInButton(view: View, checkedIn: Boolean) {
        val checkInBtn = view.findViewById<Button>(R.id.checkInBtn)
        if(checkedIn)
            checkInBtn.setBackgroundColor(Color.GREEN)
        else
            checkInBtn.setBackgroundColor(Color.GRAY)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
            }
    }
}