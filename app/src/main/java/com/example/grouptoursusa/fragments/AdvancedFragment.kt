package com.example.grouptoursusa.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.grouptoursusa.R
import com.example.grouptoursusa.data.Person
import com.example.grouptoursusa.data.PersonViewModel
import java.lang.Exception

class AdvancedFragment : Fragment() {

    private val mPersonViewModel: PersonViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_advanced, container, false)

        val deletePeopleBtn = view.findViewById<Button>(R.id.btnDeletePeople)
        val clearCheckInBtn = view.findViewById<Button>(R.id.btnClearCheckIn)
        val textAllBtn = view.findViewById<Button>(R.id.btnTextAllPeople)

        deletePeopleBtn.setOnClickListener {
            deletePeople()
        }
        clearCheckInBtn.setOnClickListener {
            resetCheckIn()
        }
        textAllBtn.setOnClickListener {
            textAll()
        }

        return view
    }

    private fun textAll() {
        var numbers = ""
        for (people in listOf(mPersonViewModel.allPeople.value)) {
            if (people != null) {
                for (person in people) {
                    numbers += person.number.toString() + ";"
                }
            }
        }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("sms:")
        intent.putExtra("address", numbers)
        intent.putExtra("sms_body", "Time to get back to the bus!")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Unable to send text", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetCheckIn() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.confirm_reset)
            .setPositiveButton(R.string.confirm,
            DialogInterface.OnClickListener { _,_ ->
                for(people in listOf(mPersonViewModel.allPeople.value)) {
                    if (people != null) {
                        for(person in people) {
                            checkInPerson(person)
                            findNavController().navigate(R.id.groupFragment)
                        }
                    }
                }
            })
            .setNegativeButton("No",
            DialogInterface.OnClickListener { _,_ -> })
        builder.create()
        builder.show()
    }

    private fun checkInPerson(person : Person) {
        person.checkedIn = false
        mPersonViewModel.checkIn(person)
    }

    private fun deletePeople() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.confirm_delete)
            .setPositiveButton(R.string.confirm,
                DialogInterface.OnClickListener { _, _ ->
                    mPersonViewModel.resetDatabase()
                    findNavController().navigate(R.id.groupFragment)
                })
            .setNegativeButton("No", DialogInterface.OnClickListener {_,_ -> })
        builder.create()
        builder.show()
    }
}