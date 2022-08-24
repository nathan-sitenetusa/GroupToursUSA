package com.example.grouptoursusa.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.grouptoursusa.R
import com.example.grouptoursusa.data.Person
import com.example.grouptoursusa.data.PersonViewModel

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

        deletePeopleBtn.setOnClickListener { deletePeople() }
        clearCheckInBtn.setOnClickListener { resetCheckIn() }
        textAllBtn.setOnClickListener { textAll() }

        return view
    }

    private fun textAll() {

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
                })
            .setNegativeButton("No", DialogInterface.OnClickListener {_,_ -> })
        builder.create()
        builder.show()
    }
}