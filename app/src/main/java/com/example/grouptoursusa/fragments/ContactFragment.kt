package com.example.grouptoursusa.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
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
import org.w3c.dom.Text
import java.lang.Exception

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
        val person = Person(selectedContactId, selectedContactName, selectedContactNumber, selectedContactNotes, selectedContactCheckedIn)
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
                    DialogInterface.OnClickListener { _,_ ->
                        deletePerson(person)
                    })
                .setNegativeButton("No",
                DialogInterface.OnClickListener { _,_ ->
                })
            builder.create()
            builder.show()
        }

        val callBtn = view.findViewById<Button>(R.id.callBtn)
        callBtn.setOnClickListener {
            call(it)
        }

        val textBtn = view.findViewById<Button>(R.id.txtBtn)
        textBtn.setOnClickListener {
            text(it)
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

    private fun call(view: View) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + navArgs<ContactFragmentArgs>().value.number.toString())
        startActivity(dialIntent)
    }

    private fun text(view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("sms:"))
        intent.putExtra("address", navArgs<ContactFragmentArgs>().value.number.toString())
        intent.putExtra("sms_body", "Time to get back to the bus!")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Unable to send text", Toast.LENGTH_SHORT).show()
        }
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ContactFragment().apply {
//            }
//    }
}