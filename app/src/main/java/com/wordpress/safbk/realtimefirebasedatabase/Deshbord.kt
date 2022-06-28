package com.wordpress.safbk.realtimefirebasedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class Deshbord : AppCompatActivity(), MyOnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private lateinit var arrayList: ArrayList<Users>
    private lateinit var dataReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deshbord)

        myInit()
        loadRecyclerView()
    }

    private fun myInit() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        arrayList = arrayListOf()
        adapter = MyAdapter(this)
        // set adapter to recyclerView
        recyclerView.adapter = adapter
    }

    // log out menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.nav_log_out -> {
                Firebase.auth.signOut()
                Toast.makeText(this, "Log Out Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadRecyclerView() {
        dataReference = FirebaseDatabase.getInstance().getReference("user")
        dataReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val userData = dataSnapshot.getValue(Users::class.java)
                        if (!arrayList.contains(userData)) {
                            arrayList.add(userData!!)
                        }
                    }
                    // send array to MyAdapter class
                    adapter.updatedArrayData(arrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Deshbord
                    , error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onClickListener(userInfo: Users) {
        val name = userInfo.name
        val email = userInfo.email
        val address = userInfo.address

        Toast.makeText(this, "Name: $name", Toast.LENGTH_SHORT).show()
    }
}