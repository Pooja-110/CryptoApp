package com.example.cryptoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpactivity)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Retrieve the stored verification ID from the intent
        val storedVerificationId = intent.getStringExtra("storedVerificationId")

        findViewById<Button>(R.id.login).setOnClickListener {
            val otp = findViewById<EditText>(R.id.et_otp).text.toString().trim()
            if (otp.isNotEmpty()) {
                // Create PhoneAuthCredential with the OTP and verification ID
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp
                )
                // Sign in with the credential
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in was successful, navigate to the main activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    } else {
                        // Handle other exceptions or errors here
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
