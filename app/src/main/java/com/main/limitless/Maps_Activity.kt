package com.main.limitless

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
//import android.telecom.Call
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import com.android.volley.Response
import com.main.limitless.databinding.ActivityMapsBinding
//import com.google.android.gms.common.internal.service.Common
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
//import javax.security.auth.callback.Callback
import com.google.android.gms.maps.model.LatLng
//import com.example.locations_ice.LatLng
import android.content.Context
import android.graphics.Color
import com.main.limitless.MapsData.Common
import com.main.limitless.MapsData.IGoogleAPIService
import com.main.limitless.MapsData.MyPlaces
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Maps_Activity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private lateinit var mLastLocation: Location
    private var mMarker: Marker? = null

    //location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    private val pathPoints: MutableList<LatLng> = mutableListOf()

    companion object {
        private const val MY_PERMISSION_CODE: Int = 2000

    }

    lateinit var mServices: IGoogleAPIService
    internal lateinit var currentPlace: MyPlaces

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Init service
        mServices = Common.googleApiService

        //request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (checkLocationPermission()) {
                buildLocationRequest()
                buildLocationCallBack()
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )
            } else {
                buildLocationRequest()
                buildLocationCallBack()
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )
            }
    }

    /*private fun nearByPlace(typePlace: String) {

        //clear all marker from map
        mMap.clear()

        //build url based on location
        val url = getUrl(latitude,longitude,typePlace)
        mServices.getNearbyPlaces(url)
            .enqueue(object: Callback<MyPlaces> {
                override fun onResponse(call: Call<MyPlaces>, response: Response<MyPlaces>) {

                    currentPlace = response.body()!!
                    if (response.isSuccessful)
                    {
                        for (i in 0 until response.body()!!.results!!.size)
                        {
                            val markerOptions= MarkerOptions()
                            val googlePlaces=response.body()!!.results!![i]
                            val lat = googlePlaces.geometry!!.location!!.lat
                            val lng = googlePlaces.geometry!!.location!!.lng
                            val placeName = googlePlaces.name
                            val latLng = LatLng(lat,lng)
                            markerOptions.position(latLng)
                            markerOptions.title(placeName)

                            if (typePlace.equals("business")) {
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cycling_icon)).title(placeName)
                            } else if (typePlace.equals("gym")) {
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.dumbell_icon)).title(placeName)
                            } else if (typePlace.equals("gas_station")) {
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.run_icon)).title(placeName)
                            } else if (typePlace.equals("restaurant")) {
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hamb_icon)).title(placeName)
                            }

                            else

                                markerOptions.icon(
                                    BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_BLUE))


                            //add marker to map
                            markerOptions.snippet(latLng.toString())// to get the lat/lng of place

                            mMap!!.addMarker(markerOptions)
                            //move camera
                            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(10f))

                        }
                    }
                }
                override fun onFailure(call: Call<MyPlaces>, t: Throwable) {
                    Toast.makeText(baseContext,""+t!!.message, Toast.LENGTH_SHORT).show()
                }
            })
    }*/

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {
        val googlePlaceUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?keyword=cruise&location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=10000") //10km
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&key=AIzaSyDTh8RbF3Jqbiwz9JnO9fhz1hpRqEbNq8s")
        Log.d("url_debug", googlePlaceUrl.toString())
        return googlePlaceUrl.toString()
    }

    /*private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                mLastLocation = p0!!.locations.get(p0
                !!.locations.size-1) // Get last location
                if (mMarker!=null)
                {
                    mMarker!!.remove()
                }
                latitude=mLastLocation.latitude
                longitude=mLastLocation.longitude

                val latlng = LatLng(latitude,longitude)
                val markerOptions = MarkerOptions()
                    .position(latlng)
                    .title("Your Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                mMarker = mMap!!.addMarker(markerOptions)

                //Move Camera
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))
            }
        }
    }*/
    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                mLastLocation = p0.locations[p0.locations.size - 1] // Get last location
                if (mMarker != null) {
                    mMarker!!.remove()
                }
                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude
                val latlng = LatLng(latitude, longitude)

                pathPoints.add(latlng) // Add new point to the path

                val markerOptions = MarkerOptions()
                    .position(latlng)
                    .title("Your Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

                mMarker = mMap.addMarker(markerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))

                pathPoints.add(latlng)
                saveRouteToSharedPreferences(pathPoints)  // Save updated route
                drawPolyline()

                drawPolyline() // Draw the updated path
            }
        }
    }

    private fun drawPolyline() {
        val polylineOptions = PolylineOptions()
            .color(Color.BLUE)
            .width(10f)
            .addAll(pathPoints)

        mMap.addPolyline(polylineOptions)
    }


        private fun buildLocationRequest() {
            locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 5000
            locationRequest.fastestInterval = 3000
            locationRequest.smallestDisplacement = 10f
        }

        private fun checkLocationPermission(): Boolean {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ), MY_PERMISSION_CODE
                    )
                else
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ), MY_PERMISSION_CODE
                    )
                return false
            } else
                return true
        }

        @SuppressLint("MissingPermission")
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                MY_PERMISSION_CODE -> {
                    if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        )
                            if (checkLocationPermission()) {
                                buildLocationRequest()
                                buildLocationCallBack()
                                fusedLocationProviderClient =
                                    LocationServices.getFusedLocationProviderClient(this)
                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest,
                                    locationCallback,
                                    Looper.myLooper()
                                )
                                mMap!!.isMyLocationEnabled = true
                            }
                    } else
                        Toast.makeText(this, "location Service Not Enabled", Toast.LENGTH_SHORT)
                }
            }
        }

        override fun onStop() {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            super.onStop()
        }


        @SuppressLint("MissingPermission")
        override fun onMapReady(googleMap: GoogleMap) {
            mMap = googleMap

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mMap!!.isMyLocationEnabled = true
                }
            } else
                mMap!!.isMyLocationEnabled = true
            //zoom control
            drawPolyline()
        }

    private fun saveRouteToSharedPreferences(route: List<LatLng>) {
        val sharedPreferences = getSharedPreferences("MyRoutes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(route)
        editor.putString("savedRoute", json)
        editor.apply()
    }

    private fun getRouteFromSharedPreferences(): List<LatLng> {
        val sharedPreferences = getSharedPreferences("MyRoutes", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("savedRoute", null)
        val type = object : TypeToken<List<LatLng>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    private fun displaySavedRoute() {
        val savedRoute = getRouteFromSharedPreferences()
        // Add your logic to display the list of saved route points
        // For example, you can iterate through the list and display markers on the map
        for (point in savedRoute) {
            val markerOptions = MarkerOptions().position(point).title("Saved Point")
            mMap.addMarker(markerOptions)
        }
        // Optionally, you can also draw the polyline of the saved route
        val polylineOptions = PolylineOptions().color(Color.RED).width(10f).addAll(savedRoute)
        mMap.addPolyline(polylineOptions)
    }


}