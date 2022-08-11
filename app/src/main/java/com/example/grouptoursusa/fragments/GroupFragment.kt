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
//        val resetBtn = view.findViewById<Button>(R.id.resetButton)
//        resetBtn.setOnClickListener() {
//            val builder = AlertDialog.Builder(requireContext())
//            builder.setMessage(R.string.confirm_clear_database)
//                .setPositiveButton(R.string.confirm,
//                    DialogInterface.OnClickListener { _,_ ->
//                        resetDatabase()
//                    })
//                .setNegativeButton("No",
//                    DialogInterface.OnClickListener { _,_ ->
//                    })
//            builder.create()
//            builder.show()
//        }

        //RecyclerView
        val adapter = GroupAdapter(findNavController())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mPersonViewModel.allPeople.observe(viewLifecycleOwner, Observer { person ->
            adapter.setData(person)
        })

        val addBtn = view.findViewById<Button>(R.id.addPersonButton)

        addBtn.setOnClickListener {
            GroupAdapter(findNavController()).navToAddFragment()
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
    private fun checkInPerson(person : Person) {
        person.checkedIn = false
        mPersonViewModel.checkIn(person)
    }
//    private fun resetDatabase() {
//        mPersonViewModel.resetDatabase()
//    }
}