package com.example.training2.Adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.training2.Classes.Photos
import com.example.training2.Classes.PhotosDatabase
import com.example.training2.Fragments.FavouriteFragment
import com.example.training2.Fragments.InterestingFragment
import com.example.training2.Fragments.RecentFragment
import com.example.training2.HelperClasses.FlickerHelperClass
import com.example.training2.R

class FlickerFavouriteAdapter () :
    RecyclerView.Adapter<FlickerFavouriteAdapter.customViewHolder>() {
    lateinit var context:Context
    constructor(myfavList: MutableList<Photos>?,context: Context):this(){
        this.context=context
        favList=myfavList
    }

    inner class customViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView =itemView.findViewById(R.id.Image)
        val favourites: ImageView = itemView.findViewById(R.id.favourite)
        val unfavourites: ImageView = itemView.findViewById(R.id.unfavourite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): customViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.flicker_image_layout, parent, false)

        return customViewHolder(view)
    }

    override fun onBindViewHolder(holder: customViewHolder, position: Int) {
        val o = favList!![position]

        val url="https://farm"+o.farm+".staticflickr.com"+"/"+o.server+"/"+o.id+"_"+o.secret+"_t_d.jpg"


        holder.favourites.visibility = View.VISIBLE
        holder.unfavourites.visibility = View.GONE

        Glide.with(context)
            .load(url)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .into(holder.image)

        Thread(Runnable {
            Glide.get(context).clearDiskCache() //1
        }).start()
        Glide.get(context).clearMemory() //2

        holder.favourites.setOnClickListener {
            holder.unfavourites.visibility = View.VISIBLE
            holder.favourites.visibility = View.GONE
            deleteThread(o.id).start()
            favList!!.removeAt(position)
            try {

                val pos1=FlickerRetrofitAdapter().returnPosition(o.id)
                Log.d("tag",pos1.toString())
                if(pos1!=-1) {
                    RecentFragment.recentAdapter!!.notifyItemChanged(pos1)
                    RecentFragment.recentAdapter!!.notifyDataSetChanged()
                }

                val pos2=FlickerRetrofitAdapter2().returnPosition(o.id)
                Log.d("tag",pos2.toString())
                if(pos2!=-1)
                 {
                     InterestingFragment.recentAdapter!!.notifyItemChanged(pos2)
                     InterestingFragment.recentAdapter!!.notifyDataSetChanged()
                }

                FavouriteFragment().emptyTV_visibility()
            } catch (e: Exception) {
                Log.d("tag",e.message.toString())
            }
        }

    }

    inner class deleteThread(val o: String) : Thread() {
        override fun run() {
            val obj = PhotosDatabase.getInstance(context)
            obj.photoDao().delete(o)
        }
    }


    override fun getItemCount(): Int {
        return favList!!.size
    }

    companion object{
        var favList:MutableList<Photos>?=null
        private const val LIST = "list"
        private const val ITEMS = "items"
        private const val ACTION = "action"
    }

    override fun onViewRecycled(holder: customViewHolder) {
        Glide.with(context).clear(holder.image)
        super.onViewRecycled(holder)
    }

}