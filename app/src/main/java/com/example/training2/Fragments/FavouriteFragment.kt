package com.example.training2.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.training2.Adapters.FlickerFavouriteAdapter
import com.example.training2.Classes.Photos
import com.example.training2.Classes.PhotosDatabase
import com.example.training2.R
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FavouriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_favourite, container, false)
        emptyTV=view.findViewById(R.id.emptyText)
        recycler=view.findViewById(R.id.recycler)

        val obj = PhotosDatabase.getInstance(requireContext())
        loadFav(obj)
        return view
    }

    fun loadFav(obj:PhotosDatabase){
        try {
            obj.photoDao().getFavPhotos().observe(viewLifecycleOwner,
                { t ->
                    list=t
                    recycler!!.adapter = FlickerFavouriteAdapter(list,requireContext())
                    emptyTV_visibility()
                })
        }
        catch(e: Exception){
            Log.d("tag",e.message.toString())
        }
    }


    fun emptyTV_visibility(){
        if(recycler!!.adapter!!.itemCount==0 || recycler!!.adapter==null){
            emptyTV!!.visibility=View.VISIBLE
        }
        else{
            emptyTV!!.visibility=View.GONE
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("tag","Detach")
        Thread(Runnable {
            Glide.get(requireContext()).clearDiskCache() //1
        }).start()
        Glide.get(requireContext()).clearMemory() //2
    }

    companion object {
        var emptyTV:TextView?=null
        var recycler:RecyclerView?=null
        var list:MutableList<Photos>? =null
    }
}