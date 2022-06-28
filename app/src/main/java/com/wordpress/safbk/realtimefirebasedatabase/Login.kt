package com.wordpress.safbk.realtimefirebasedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class Login : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var registration: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        auth = Firebase.auth

        myInit()
    }

    private fun myInit() {
        email = findViewById(R.id.edt_email)
        password = findViewById(R.id.edt_password)
        login  = findViewById(R.id.btn_login)
        registration = findViewById(R.id.tv_register)

        login.setOnClickListener {
            logIn()
        }

        registration.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    private fun logIn() {
        val username = email.text.toString().trim()
        val password = password.text.toString().trim()

        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateUi()
                    Toast.makeText(this, "Log In Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUi();
        }
    }

    private fun updateUi() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
