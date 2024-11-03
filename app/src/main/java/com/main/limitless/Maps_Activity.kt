package com.main.limitless

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.main.limitless.MapsData.Common
import com.main.limitless.MapsData.IGoogleAPIService
import com.main.limitless.MapsData.MyPlaces
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.main.limitless.Exercise.Exercise_Activity
import com.main.limitless.databinding.ActivityMapsBinding

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

    private var isMapReady = false

    private var startTime: Long = 0L
    private var elapsedTime: Long = 0L
    private var timerRunning = false
    private lateinit var timerHandler: Handler
    private lateinit var timerRunnable: Runnable
    private var totalDistance: Float = 0f

    private lateinit var distances: TextView
    private lateinit var time: TextView

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mServices = Common.googleApiService

        time = findViewById(R.id.m_Time)
        time.text = getString(R.string.time_00_00_00)
        distances = findViewById(R.id.m_Distance)
        distances.text = getString(R.string.distance_0_00_km)

        // Initialize timer
        timerHandler = Handler(Looper.getMainLooper())
        timerRunnable = object : Runnable {
            override fun run() {
                val currentTime = System.currentTimeMillis()
                elapsedTime = (currentTime - startTime) / 1000
                val hours = elapsedTime / 3600
                val minutes = (elapsedTime % 3600) / 60
                val seconds = elapsedTime % 60
                val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                time.text = getString(R.string.time, formattedTime)
                timerHandler.postDelayed(this, 1000)
            }
        }

        val startButton: Button = findViewById(R.id.btnStart_M)
        startButton.setOnClickListener {
            startTimer()
        }

        val stopButton: Button = findViewById(R.id.btnStop_M)
        stopButton.setOnClickListener {
            stopTimer()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        val close: TextView = findViewById(R.id.m_Close)

        close.setOnClickListener{
            val intent = Intent(this, Exercise_Activity::class.java)
            startActivity(intent)
        }
    }

    private fun startTimer() {
        if (!timerRunning) {
            startTime = System.currentTimeMillis()
            totalDistance = 0f
            timerHandler.postDelayed(timerRunnable, 0)
            timerRunning = true
        }
    }

    private fun stopTimer() {
        if (timerRunning) {
            timerHandler.removeCallbacks(timerRunnable)
            timerRunning = false
        }
    }

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

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                val newLocation = p0.locations.last()
                if (::mLastLocation.isInitialized && timerRunning) {
                    val distance = mLastLocation.distanceTo(newLocation)
                    totalDistance += distance

                    val distanceInKilometers = totalDistance / 1000

                    val formattedDistance = String.format("%.2f", distanceInKilometers)
                    distances.text = getString(R.string.distance_km, formattedDistance)
                }
                mLastLocation = newLocation

                if (mMarker != null) {
                    mMarker!!.remove()
                }
                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude
                val latLng = LatLng(latitude, longitude)

                pathPoints.add(latLng)

                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("Your Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

                mMarker = mMap.addMarker(markerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))

                drawPolyline()
            }
        }
    }


    private fun drawPolyline() {
       if (!isMapReady) return // Ensure the map is ready
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

        private fun checkLocationPermission():Boolean {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            }
            else
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
            isMapReady = true
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

            drawPolyline()
        }
}