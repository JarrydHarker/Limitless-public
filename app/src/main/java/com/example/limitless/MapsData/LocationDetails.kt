package com.example.limitless.MapsData

data class Results (
    var photos:Array<Photos>?=null,
    var id:String?=null,
    var place_id:String?=null,
    var price_level:Int=0,
    var rating:Double=0.0,
    var user_ratings_total:Int=0,
    var reference:String?=null,
    var scope:String?=null,
    var types:Array<String>?=null,
    var vicinity:String?=null,
    var opening_hours:OpeningHours?=null,
    var name:String?=null,
    var icon:String?=null,
    var geometry:Geometry?=null,
    var business_status:String?=null
)

data class Geometry(
    var viewport:Viewport?=null,
    var location: Location?=null
)

data class LatLng(
    val lat: Double,
    val lng: Double
)

class MyPlaces (
    var html_attributions: Array<String>? = null,
    var status : String?=null,
    var results:Array<Results>?=null
)

data class Photo(
    val height: Int?,
    val html_attributions: List<String>?,
    val photo_reference: String?,
    val width: Int?
)

data class PlusCode(
    val compound_code: String?,
    val global_code: String?
)
data class Location (

    var lat:Double=0.0,
    var lng:Double=0.0
)
data class Northeast (
    var lat:Double=0.0,
    var lng:Double=0.0
)
data class OpeningHours (
    var open_now:Boolean=false
)
class Photos (
    var height:Int=0,
    var width:Int=0,
    var photo_reference:String?=null,
    var html_attributions:Array<String>?=null
)
data class Southwest (
    var lat:Double=0.0,
    var lng:Double=0.0
)
data class Viewport (
    var northeast:Northeast?=null,
    var southwest:Southwest?=null
)