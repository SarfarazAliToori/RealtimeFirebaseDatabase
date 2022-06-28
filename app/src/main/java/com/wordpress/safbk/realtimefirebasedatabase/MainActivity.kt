package com.wordpress.safbk.realtimefirebasedatabase

import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {

    private lateinit var btnLoadGallaryImage: Button
    private lateinit var btnUploadImage2FirebaseDatabase: Button
    private lateinit var btnMove2Deshbord: Button
    private lateinit var btnRetriveImageFromFirebase: Button

    private lateinit var imageView: ImageView

    private lateinit var uri: Uri

    private var storageRef = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myInit()
    }

    private fun myInit() {

        supportActionBar?.hide()

        // initialization of firebase storage
        storageRef = FirebaseStorage.getInstance()

        btnLoadGallaryImage = findViewById(R.id.btn_load_image_from_gellary)
        btnUploadImage2FirebaseDatabase = findViewById(R.id.btn_upload_2_firebase)
        btnMove2Deshbord = findViewById(R.id.btn_move_2_deshbord)
        imageView = findViewById(R.id.imageView)
        btnRetriveImageFromFirebase = findViewById(R.id.btn_retrive_image)

        btnMove2Deshbord.setOnClickListener {
            startActivity(Intent(this, Deshbord::class.java))
        }

        btnUploadImage2FirebaseDatabase.setOnClickListener {
            uploadImage()
        }

        // load image from gallery
        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imageView.setImageURI(it)
                // it have image uri so that's why We need uri in uploading image to firebase
                uri = it
            }
        )
        btnLoadGallaryImage.setOnClickListener {
            galleryImage.launch("image/*")
        }

        btnRetriveImageFromFirebase.setOnClickListener {
            startActivity(Intent(this, RetriveImageFromFirebase::class.java))
        }

    }

    private fun uploadImage() {
        storageRef.getReference("images")
            .child(System.currentTimeMillis().toString())
            .putFile(uri)
            .addOnSuccessListener {task ->
                task.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener {
                        val userId = FirebaseAuth.getInstance().currentUser!!.uid
                        val imageMap = mapOf(
                            "url" to it.toString()
                        )
                        val databaseRef = FirebaseDatabase.getInstance().getReference("userImages")
                        databaseRef.child(userId).setValue(imageMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "uploaded successfully",
                                    Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .addOnFailureListener { error ->
                                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                            }
                    }
            }
    }
}