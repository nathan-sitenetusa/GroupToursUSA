package com.example.grouptoursusa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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
//        val recyclerView = findViewById<RecyclerView>(R.id.contactsView)
//        recyclerView.adapter = ContactAdapter(this, contacts)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(this)
    }

//    private val navHostFragment = supportFragmentManager.findFragmentById(R.id.groupFragment) as NavHostFragment
//    val navController = navHostFragment.navController
//
//    fun onClick(navController: NavController) {
//        val action = GroupFragmentDirections.viewContact()
//        navController.navigate(action)
//    }
}