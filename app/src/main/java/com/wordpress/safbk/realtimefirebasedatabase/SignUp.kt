package com.wordpress.safbk.realtimefirebasedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var address: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var alreadyRegi: TextView
    private lateinit var signUp: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        // Initialize Firebase Auth
        auth = Firebase.auth
        database = Firebase.database.reference

        myInit()
    }

    private fun myInit() {
        name = findViewById(R.id.edt_name_s)
        address = findViewById(R.id.edt_address_s)
        email = findViewById(R.id.edt_email_s)
        password = findViewById(R.id.edt_password_s)
        alreadyRegi = findViewById(R.id.tv_I_am_already_registered)
        signUp = findViewById(R.id.btn_signUp_s)

        signUp.setOnClickListener {
            mSignUp()
        }

        alreadyRegi.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun mSignUp() {

        val email = email.text.toString().trim()
        val password = password.text.toString().trim()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    saveInRealTimeDatabase(user)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun saveInRealTimeDatabase(user: FirebaseUser?) {
        val name = name.text.toString().trim()
        val address = address.text.toString().trim()
        val email = email.text.toString().trim()
        val password = password.text.toString().trim()

        val userData = Users(name,email,address,password)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("user").child(userId).setValue(userData)

        Toast.makeText(this, "Data Added Successfully", Toast.LENGTH_SHORT).show()

    }

    private fun updateUI(user: FirebaseUser?) {
        Toast.makeText(this, "Log in Successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}