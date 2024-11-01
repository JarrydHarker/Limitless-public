package com.example.limitless.MapsData

import com.example.limitless.MapsData.IGoogleAPIService

object Common {
    var currentResult: Results? = null

    private val GOOGLE_API_URL="https://maps.googleapis.com/"
    val googleApiService: IGoogleAPIService
        get()=RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}