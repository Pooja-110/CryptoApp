package com.example.cryptoapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class VarificationActivity: AppCompatActivity() {

    var number : String =""

    lateinit var auth: FirebaseAuth

    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_varification)

        auth=FirebaseAuth.getInstance()

        findViewById<Button>(R.id.button_otp).setOnClickListener {
            login()
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
                Log.d("GFG" , "onVerificationCompleted Success")
            }


            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("GFG" , "onVerificationFailed  $e")
            }


            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG","onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token


                val intent = Intent(applicationContext,OTPActivity::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun login() {
        number = findViewById<EditText>(R.id.et_phone_number).text.trim().toString()

        if (number.isNotEmpty()){
            number = "+91$number"
            sendVerificationCode(number)
        }else{
            Toast.makeText(this,"Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("GFG" , "Auth started")
    }


}