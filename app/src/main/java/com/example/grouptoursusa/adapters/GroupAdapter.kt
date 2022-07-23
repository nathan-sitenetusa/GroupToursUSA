package com.example.grouptoursusa.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.grouptoursusa.R
import com.example.grouptoursusa.data.Person

class GroupAdapter: RecyclerView.Adapter<GroupAdapter.ContactViewHolder>() {

    private var people = emptyList<Person>()

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_item, parent, false)

        return ContactViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        var item = people[position]
        holder.itemView.findViewById<TextView>(R.id.nameText).text = item.name
        holder.itemView.findViewById<TextView>(R.id.phoneNumberText).text = item.number.toString()
        holder.itemView.findViewById<TextView>(R.id.idText).text = item.id.toString()
        var layout = holder.itemView.findViewById<ConstraintLayout>(R.id.ConstraintLayout)
        layout.setOnClickListener() {
            synchronized(this) {
                if (item.checkedIn) {
                    layout.setBackgroundColor(Color.GREEN)
                } else {
                    layout.setBackgroundColor(Color.WHITE)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return people.size
    }

    fun setData(people: List<Person>) {
        this.people = people
        notifyDataSetChanged()
    }
}