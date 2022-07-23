package com.example.training2.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.training2.Adapters.FlickerRetrofitAdapter
import com.example.training2.Adapters.FlickerRetrofitAdapter2
import com.example.training2.Classes.FlickerRetrofitClass
import com.example.training2.Classes.PaginatorScrollListener
import com.example.training2.HelperClasses.FlickerHelperClass
import com.example.training2.Interfaces.FlickerRetrofitInterface
import com.example.training2.R
import com.google.gson.JsonObject
import org.json.JSONException
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class InterestingFragment : Fragment() {
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
        val view=inflater.inflate(R.layout.fragment_interesting, container, false)
        val gridLayoutManager= GridLayoutManager(context,2)
         recycler =view.findViewById(R.id.recycler)
         recycler!!.layoutManager=gridLayoutManager
         recentAdapter = FlickerRetrofitAdapter2(requireContext())
         recycler!!.adapter=  recentAdapter
         flickerRetrofitInterface =FlickerRetrofitClass.getRetrofit().create(FlickerRetrofitInterface::class.java)
         recycler!!.addOnScrollListener(object: PaginatorScrollListener(gridLayoutManager) {
            override fun loadMoreItems() {
                 isloading =true
                 currentPage += 1
                loadNextPage();
            }

            override fun isLastPage(): Boolean {
                return  islastPage
            }

            override fun isLoading(): Boolean {
                return  isloading
            }

        })
        loadFirstPage()
        return view
    }
    fun loadFirstPage(){
        flickerRetrofitInterface!!.getInteresting(currentPage,1).enqueue(object:retrofit2.Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    val list=ArrayList<FlickerHelperClass>()
                    val jsonObject=response.body()
                    TOTAL_PAGES=jsonObject!!.getAsJsonObject("photos").get("pages").asInt
                    val jsonArray = jsonObject.getAsJsonObject("photos").getAsJsonArray("photo")
                    for (i in 0 until jsonArray!!.size()) {
                        val obj=jsonArray.get(i) as JsonObject
                        list.add(FlickerHelperClass(obj.get("id").asString,
                            obj.get("owner").asString,
                            obj.get("secret").asString,
                            obj.get("server").asString,
                            obj.get("farm").asInt,
                            obj.get("title").asString,
                            obj.get("isfriend").asInt,
                            obj.get("ispublic").asInt,
                            obj.get("isfamily").asInt))
                    }
                    recentAdapter!!.addAll(list)
                    loadingBar!!.visibility=View.GONE
                    if (currentPage <= TOTAL_PAGES!!) recentAdapter!!.addLoadingFooter();
                    else islastPage = true;
                } catch (e: Exception) {
                    Log.d("tag",e.message.toString())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("tag", "Error : $t")
            }


        })
    }

    private fun loadNextPage() {
        flickerRetrofitInterface!!.getInteresting(currentPage,1).enqueue(object:retrofit2.Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                recentAdapter!!.removeLoadingFooter()
                isloading=false
                try {
                    val list=ArrayList<FlickerHelperClass>()
                    val jsonObject=response.body()
                    TOTAL_PAGES=jsonObject!!.getAsJsonObject("photos").get("pages").asInt
                    val jsonArray = jsonObject.getAsJsonObject("photos").getAsJsonArray("photo")
                    for (i in 0 until jsonArray!!.size()) {
                        val obj=jsonArray.get(i) as JsonObject
                        list.add(FlickerHelperClass(obj.get("id").asString,
                            obj.get("owner").asString,
                            obj.get("secret").asString,
                            obj.get("server").asString,
                            obj.get("farm").asInt,
                            obj.get("title").asString,
                            obj.get("isfriend").asInt,
                            obj.get("ispublic").asInt,
                            obj.get("isfamily").asInt))
                    }
                    recentAdapter!!.addAll(list)
                    if (currentPage <= TOTAL_PAGES!!) recentAdapter!!.addLoadingFooter();
                    else islastPage = true;
                } catch (e: Exception) {
                    Log.d("tag",e.message.toString())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("tag", "Error : $t")
            }


        })
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
        var recycler:RecyclerView?=null
        var loadingBar:RelativeLayout?=null
        var recentAdapter:FlickerRetrofitAdapter2?=null
        var flickerRetrofitInterface:FlickerRetrofitInterface?=null
        private const val PAGE_START = 1
        var isloading = false
        var islastPage = false
        var TOTAL_PAGES:Int?=null
        private var currentPage = PAGE_START
    }
}