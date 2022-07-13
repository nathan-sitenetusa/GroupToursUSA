package com.example.grouptoursusa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grouptoursusa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

//    private val navHostFragment = supportFragmentManager.findFragmentById(R.id.groupFragment) as NavHostFragment
//    val navController = navHostFragment.navController
//
//    fun onClick(navController: NavController) {
//        val action = GroupFragmentDirections.viewContact()
//        navController.navigate(action)
//    }
}