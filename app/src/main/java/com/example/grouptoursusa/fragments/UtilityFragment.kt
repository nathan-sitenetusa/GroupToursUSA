package com.example.grouptoursusa.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.grouptoursusa.R
import com.example.grouptoursusa.data.Person
import com.example.grouptoursusa.data.PersonViewModel

class UtilityFragment : Fragment() {

    private val mPersonViewModel: PersonViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_utility, container, false)

        val id = navArgs<UtilityFragmentArgs>().value.id
        val name = navArgs<UtilityFragmentArgs>().value.name
        val phone = navArgs<UtilityFragmentArgs>().value.phone
        val notes = navArgs<UtilityFragmentArgs>().value.notes
        val checkedIn = navArgs<UtilityFragmentArgs>().value.checkedIn

        val person = Person(id, name, phone, notes, checkedIn)

        mPersonViewModel.checkIn(person)

        Toast.makeText(requireContext(), "Check-in Updated", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.groupFragment)

        return view
    }
}