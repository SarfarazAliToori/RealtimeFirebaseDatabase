package com.wordpress.safbk.realtimefirebasedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RetriveImageFromFirebase : AppCompatActivity() {

    private lateinit var ivRetrieveImage: ImageView
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrive_image_from_firebase)

        ivRetrieveImage = findViewById(R.id.iv_retrive_image)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        databaseRef = FirebaseDatabase.getInstance().getReference("userImages")
            .child(userId)
        databaseRef.get().addOnSuccessListener {
            val url = it.child("url").value.toString()

            Glide.with(this).load(url).into(ivRetrieveImage)
            Toast.makeText(this, "image is successfully loaded", Toast.LENGTH_SHORT).show()

        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }


    }
}