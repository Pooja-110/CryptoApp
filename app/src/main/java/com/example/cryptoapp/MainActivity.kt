package com.example.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController=navHostFragment!!.findNavController()

        val popupMenu=PopupMenu(this, View(this))
        popupMenu.inflate(R.menu.bottomnav_menu)

        binding.bottomBar.setupWithNavController(popupMenu.menu,navController)

    }

}