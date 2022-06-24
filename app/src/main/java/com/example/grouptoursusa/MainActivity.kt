package com.example.grouptoursusa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grouptoursusa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contacts: List<String> = listOf("Nathan", "Matt", "Mikkel", "Kevin", "Ebare", "Byron")
        val recyclerView = findViewById<RecyclerView>(R.id.contactsView)
        recyclerView.adapter = ContactAdapter(this, contacts)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fun dont() {
            print("We have saved humanity")
        }
        fun dontnot() {
            print("We're screwed")
        }

        val goingToCrash : Boolean = true
        if(goingToCrash)
        {
            dont()
        }
        else {
            dontnot()
        }
    }
}