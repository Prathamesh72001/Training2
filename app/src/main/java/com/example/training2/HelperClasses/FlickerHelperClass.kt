package com.example.training2.HelperClasses

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import kotlin.properties.Delegates

class FlickerHelperClass() {

    lateinit var id:String
    lateinit var owner: String
    lateinit var secret: String
    lateinit var server: String
    var farm by Delegates.notNull<Int>()
    lateinit var title: String
    var isfriend by Delegates.notNull<Int>()
    var ispublic by Delegates.notNull<Int>()
    var isfamily by Delegates.notNull<Int>()

    constructor(id:String,owner:String,secret:String,server:String,farm:Int,title:String,isfriend:Int,ispublic:Int,isfamily:Int):this(){
        this.id=id
        this.owner=owner
        this.secret=secret
        this.server=server
        this.farm=farm
        this.title=title
        this.isfriend=isfriend
        this.ispublic=ispublic
        this.isfamily=isfamily
    }


    /*var id:String
        get() {
            return obj!!.get("id").asString
        }set(value) {
        id=value
    }

    var owner:String
        get() {
            return obj!!.get("owner").asString
        }
        set(value) {
            owner=value
        }

    var secret:String
        get() {
            return obj!!.get("secret").asString
        }
        set(value) {
            secret=value
        }

    var server:String
        get() {
            return obj!!.get("server").asString
        }
        set(value) {
            server=value
        }

    var farm:Int
        get() {
            return obj!!.get("farm").asInt
        }
        set(value) {
            farm=value
        }

    var title:String
        get() {
            return obj!!.get("title").asString
        }
        set(value) {
            title=value
        }

    var isfriend:Int
        get() {
            return obj!!.get("isfriend").asInt
        }
        set(value) {
            isfriend=value
        }

    var ispublic:Int
        get() {
            return obj!!.get("ispublic").asInt
        }
        set(value) {
            ispublic=value
        }

    var isfamily:Int
        get() {
            return obj!!.get("isfamily").asInt
        }
        set(value) {
            isfamily=value
        }*/
}