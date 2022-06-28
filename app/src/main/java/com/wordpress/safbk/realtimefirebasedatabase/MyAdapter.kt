package com.wordpress.safbk.realtimefirebasedatabase

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.core.Context

class MyAdapter(val listener: MyOnClickListener) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    val arrayList: ArrayList<Users> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_view,parent,false)
        val myView = MyViewHolder(view)

        view.setOnClickListener {
            listener.onClickListener(arrayList[myView.adapterPosition])
        }

        return myView
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = arrayList[position]
        holder.namee.text = currentItem.name
        holder.email.text = currentItem.email
        holder.address.text = currentItem.address
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun updatedArrayData(updatedArrayList: ArrayList<Users>) {
        arrayList.clear()
        arrayList.addAll(updatedArrayList)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namee  = itemView.findViewById<TextView>(R.id.tv_c_name)
        val address = itemView.findViewById<TextView>(R.id.tv_c_address)
        val email = itemView.findViewById<TextView>(R.id.tv_c_email)

    }

}