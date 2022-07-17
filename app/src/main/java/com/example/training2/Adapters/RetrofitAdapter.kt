package com.example.training2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.training2.HelperClasses.StudentHelperClass
import com.example.training2.HelperClasses.StudentHelperClass2
import com.example.training2.R

class RetrofitAdapter (val list:ArrayList<StudentHelperClass2>?): RecyclerView.Adapter<RetrofitAdapter.customViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RetrofitAdapter.customViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.retrofit_layout, parent, false)

        return customViewHolder(view)
    }

    override fun onBindViewHolder(holder: RetrofitAdapter.customViewHolder, position: Int) {
        val obj=list!![position]

        holder.id.text="ID : "+obj.id.toString()
        holder.name.text="Name : "+obj.name
        holder.age.text="Age : "+obj.age.toString()
        var s=""
        for(i in 0 until obj.marks.size()){
            s+=obj.marks.get(i).asString + " "
        }
        holder.marks.text= "Marks : $s"
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class customViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val id: TextView =itemView.findViewById(R.id.id)
        val name: TextView =itemView.findViewById(R.id.name)
        val age: TextView =itemView.findViewById(R.id.age)
        val marks: TextView =itemView.findViewById(R.id.marks)
    }
}