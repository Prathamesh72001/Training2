package com.example.training2.HelperClasses

import org.json.JSONArray
import org.json.JSONObject

class StudentHelperClass(val obj:JSONObject) {
    var id:Int
        get() {
            return obj.getInt("id")
        }set(value) {
            id=value
        }

    var name:String
        get() {
            return obj.getString("name")
        }
        set(value) {
            name=value
        }

    var age:Int
        get() {
            return obj.getInt("age")
        }
        set(value) {
            age=value
        }

    var marks:JSONArray
        get() {
            return obj.getJSONArray("marks")
        }
        set(value) {
            marks=value
        }
}