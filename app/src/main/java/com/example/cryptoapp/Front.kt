package com.example.cryptoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed

class Front : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front)

        Handler(Looper.getMainLooper()).postDelayed(3000){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}