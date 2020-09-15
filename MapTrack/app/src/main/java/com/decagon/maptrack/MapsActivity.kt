package com.decagon.maptrack

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.decagon.maptrack.model.MyLocationLog
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        dbReference = Firebase.database.reference
        dbReference.addValueEventListener(eventListener)


    }


    /**
     * Read partner's location from database by listening to change of her location
     */
    private val eventListener = object : ValueEventListener {

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(applicationContext, "Could not read from database", Toast.LENGTH_LONG)
                .show()
        }


        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                map.clear()
                val locationLog =
                    dataSnapshot.child("komesLocation").getValue(MyLocationLog::class.java)

                var partnerLatitude = locationLog?.Latitude
                var partnerLongitude = locationLog?.Longitude


                if (partnerLatitude != null && partnerLongitude != null) {
                    val partnerLocation = LatLng(partnerLatitude, partnerLongitude)

                    val markerOptions = MarkerOptions().position(partnerLocation).title("Kome")
                    val zoomLevel = 20f

                    map.setMinZoomPreference(zoomLevel)
                    map.addMarker(markerOptions)
                    map.animateCamera(CameraUpdateFactory.newLatLng(partnerLocation))


//                    Toast.makeText(
//                        applicationContext,
//                        "Partner's Location accessed from the database",
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.setAllGesturesEnabled(true)

        getLocationAccess()

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(latitude, longitude)
//        map.setMinZoomPreference(zoomLevel)
//        map.uiSettings.isZoomControlsEnabled = true
//        map.uiSettings.setAllGesturesEnabled(true)
//        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            getLocationUpdates()
            startLocationUpdates()
        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    this,
                    "User has not granted location access permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    /**
     * Set location updates
     */
    private fun getLocationUpdates() {
        locationRequest = LocationRequest()


        val MIN_TIME = 10000L
        val TIME_INTERVAL = 20000L


        //Set intervals
        locationRequest.interval = TIME_INTERVAL
        locationRequest.fastestInterval = MIN_TIME
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation

                    dbReference = Firebase.database.reference

                    val locationLogging = MyLocationLog(location.latitude, location.longitude)
                    dbReference.child("dikasLocation").setValue(locationLogging)
                        .addOnSuccessListener {
//                            Toast.makeText(applicationContext, "Locations written into the database", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
//                            Toast.makeText(
//                                applicationContext,
//                                "Error occured while writing the locations",
//                                Toast.LENGTH_LONG
//                            ).show()
                        }

                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        val markerOptions = MarkerOptions().position(latLng)
                        val zoomLevel = 20f

                        map.setMinZoomPreference(zoomLevel)
                        map.addMarker(markerOptions)
//                        map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    }
                }
            }
        }
    }

    /**
     * Register location callback with location client
     */
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }


}