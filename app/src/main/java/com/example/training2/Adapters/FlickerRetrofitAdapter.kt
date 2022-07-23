package com.example.training2.Adapters

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.training2.Classes.Photos
import com.example.training2.Classes.PhotosDatabase
import com.example.training2.Fragments.FavouriteFragment
import com.example.training2.HelperClasses.FlickerHelperClass
import com.example.training2.R

import java.util.*


class FlickerRetrofitAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var context: Context? = null

    constructor(context:Context):this(){
        this.context=context
        list=LinkedList()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        var viewHolder:RecyclerView.ViewHolder?=null

        when (viewType) {
            ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.flicker_image_layout, parent, false)
                viewHolder = customViewHolder(view)
            }
            LOADING -> {
                val view =LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading_layout, parent, false)
                viewHolder = loadingViewHolder(view)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj=list!![position]

        when (getItemViewType(position)) {
            ITEM -> {
                val myholder: customViewHolder = holder as customViewHolder

                val url="https://farm"+obj.farm+".staticflickr.com"+"/"+obj.server+"/"+obj.id+"_"+obj.secret+"_t_d.jpg"

                Glide.with(context!!)
                    .load(url)
                    .skipMemoryCache(true) //2
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //3
                    .into(myholder.image)

                val favlist=FavouriteFragment.list
                var i=0
                while(favlist!!.size>i){
                    Log.d("tag",favlist[i].id)
                    if(obj.id==favlist[i].id)
                    {
                        myholder.favourites.visibility=View.VISIBLE
                        myholder.unfavourites.visibility=View.GONE
                        break
                    }
                    else{
                        myholder.favourites.visibility=View.GONE
                        myholder.unfavourites.visibility=View.VISIBLE
                        i++
                    }
                }

                myholder.favourites.setOnClickListener {
                    myholder.unfavourites.visibility = View.VISIBLE
                    myholder.favourites.visibility = View.GONE
                    deleteThread(obj.id).start()
                    try {
                        FavouriteFragment().emptyTV_visibility()
                    } catch (e: Exception) {

                    }
                }

                myholder.unfavourites.setOnClickListener {
                    myholder.favourites.visibility = View.VISIBLE
                    myholder.unfavourites.visibility = View.GONE
                    val o=Photos(obj.id,obj.owner,obj.secret,obj.server,obj.farm,obj.title,obj.isfriend,obj.ispublic,obj.isfamily)
                    insertThread(o).start()
                    try {
                        FavouriteFragment().emptyTV_visibility()
                    } catch (e: Exception) {

                    }
                }

            }
            LOADING -> {
                val loadholder: loadingViewHolder = holder as loadingViewHolder
                loadholder.loadingbar.setVisibility(View.VISIBLE)
            }
        }

    }

    inner class deleteThread(val o: String) : Thread() {
        override fun run() {
            val obj = PhotosDatabase.getInstance(context!!)
            obj.photoDao().delete(o)
        }
    }

    inner class insertThread(val o: Photos) : Thread() {
        override fun run() {
            val obj = PhotosDatabase.getInstance(context!!)
            obj.photoDao().insert(o)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(FlickerHelperClass())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = list!!.size - 1
        val result = getItem(position)
        list!!.remove(result)
        notifyItemRemoved(position)
    }

    fun add(obj: FlickerHelperClass) {
        list!!.add(obj)
        notifyItemInserted(list!!.size - 1)
    }

    fun addAll(Results: List<FlickerHelperClass>) {
        for (result in Results) {
            add(result)
        }
    }

    fun getItem(position: Int): FlickerHelperClass {
        return list!!.get(position)
    }


    override fun getItemCount(): Int {
        return list!!.size
    }

    fun returnPosition(oid:String):Int{
        var index=-1
        for(i in 0 until  list!!.size){
            if(list!![i].id.equals(oid)){
                index=i
                break
            }
        }
        return index

    }

    inner class customViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView =itemView.findViewById(R.id.Image)
        val favourites: ImageView =itemView.findViewById(R.id.favourite)
        val unfavourites: ImageView =itemView.findViewById(R.id.unfavourite)

    }

    inner class loadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val loadingbar:RelativeLayout=itemView.findViewById(R.id.loadingbar)
    }

    companion object{
        var isexist=false
        private const val LOADING = 0
        private const val ITEM = 1
        private const val LIST = "list"
        private const val ITEMS = "items"
        private const val ACTION = "action"
        private var isLoadingAdded = false
        private var list: MutableList<FlickerHelperClass>? = null
    }

}