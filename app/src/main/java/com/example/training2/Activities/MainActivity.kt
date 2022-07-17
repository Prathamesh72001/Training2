package com.example.training2.Activities

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.training2.Adapters.StudentAdapter
import com.example.training2.HelperClasses.StudentHelperClass
import com.example.training2.R
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.recycler)
        MyJSONTask().execute()

    }

    inner class MyJSONTask : AsyncTask<Void?, Void?, String?>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void?): String? {
            var urlConnection: HttpURLConnection? = null
            var bufferedReader: BufferedReader? = null
            try {
                val url = URL("http://demo3102633.mockable.io/")
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connect()
                val inputStream= urlConnection.getInputStream()
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuffer = StringBuffer()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuffer.append(line).append("\n")
                }
                if (stringBuffer.length == 0) {
                    return null
                } else {
                    return stringBuffer.toString()
                }
            } catch (e: Exception) {
                return null
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        override fun onPostExecute(jsonStr: String?) {
            super.onPostExecute(jsonStr)
            if (jsonStr != null) {

                val list = ArrayList<StudentHelperClass>()
                try {
                    val jsonObject = JSONObject(jsonStr)
                    val jsonArray = jsonObject.getJSONArray("Students")
                    for (i in 0 until jsonArray.length()) {
                        val obj=jsonArray.getJSONObject(i)
                        list.add(StudentHelperClass(obj))
                    }
                    recyclerView!!.adapter=StudentAdapter(list)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }


    }

    companion object{
        var recyclerView:RecyclerView?=null
    }
}