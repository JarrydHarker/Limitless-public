package com.example.limitless.MapsData

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IGoogleAPIService {
    @GET("maps/api/place/nearbysearch/json")
    fun getNearbyPlaces(@Url url: String): Call<MyPlaces>
}