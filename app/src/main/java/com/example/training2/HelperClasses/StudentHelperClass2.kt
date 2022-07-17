package com.example.training2.HelperClasses

import com.google.gson.JsonArray
import com.google.gson.JsonObject

class StudentHelperClass2(val obj: JsonObject) {
    var id:Int
        get() {
            return obj.get("id").asInt
        }set(value) {
        id=value
    }

    var name:String
        get() {
            return obj.get("name").asString
        }
        set(value) {
            name=value
        }

    var age:Int
        get() {
            return obj.get("age").asInt
        }
        set(value) {
            age=value
        }

    var marks: JsonArray
        get() {
            return obj.get("marks").asJsonArray
        }
        set(value) {
            marks=value
        }
}