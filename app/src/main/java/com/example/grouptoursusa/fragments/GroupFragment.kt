package com.example.grouptoursusa.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grouptoursusa.R
import com.example.grouptoursusa.adapters.GroupAdapter
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

        val advancedBtn = view.findViewById<Button>(R.id.advancedMenu)
        advancedBtn.setOnClickListener {
            GroupAdapter(findNavController()).navToAdvancedFragment()
        }

        return view
    }
}