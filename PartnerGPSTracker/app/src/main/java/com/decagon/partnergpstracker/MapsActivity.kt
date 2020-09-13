package com.decagon.partnergpstracker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1

    lateinit var dbReference: DatabaseReference
    lateinit var manager: LocationManager
    lateinit var myMarker: Marker

    val MIN_TIME = 1000L //1 sec
    val MIN_DISTANCE = 0.2f //1 meter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        //FirebaseDatabase.getInstance().reference.setValue("this is Dika and Kome tracker app")


        dbReference = FirebaseDatabase.getInstance().reference.child("user-102")

        getLocationUpdates()

        readChanges()
    }

    private fun readChanges() {
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
//                    var location = MyLocation()
//                    var latitude = location.

                    try {
                        var location: MyLocation? = dataSnapshot.getValue(MyLocation().javaClass)
                        if (location != null) {
                            myMarker.position = LatLng(location.getLatitude(), location.getLongitude())
                        }
//                val latitude= dataSnapshot.getValue<Double>()
                    } catch (e: Exception) {
                        Toast.makeText(baseContext, "Exception message $e", Toast.LENGTH_SHORT).show()
                        Log.i("TAG", "onDataChange:$e ")
                    }
                }
            }
        })
    }


    private fun getLocationUpdates() {
        manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (manager != null) {
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
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
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this)
            } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
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
        val zoomLevel = 20f
        // Add a marker in Sydney and move the camera
        val myPosition = LatLng(37.421998333333335, -122.08400000000002)

        myMarker = map.addMarker(MarkerOptions().position(myPosition).title("My Marker in Uno"))
        map.addMarker(MarkerOptions().position(myPosition).title("My Marker in Uno"))
        map.setMinZoomPreference(zoomLevel)
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.setAllGesturesEnabled(true)
        map.moveCamera(CameraUpdateFactory.newLatLng(myPosition))

    }


    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) === PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (isPermissionGranted() && requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && (grantResults[0] === PackageManager.PERMISSION_GRANTED)) {
//                enableMyLocation()
                getLocationUpdates()
            } else {
                Toast.makeText(this, "Permission is required", Toast.LENGTH_LONG).show()
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_LOCATION_PERMISSION)
            }
        }
    }


    override fun onLocationChanged(location: Location) {
        if (location != null) {
            saveLocation(location)
        } else {
            Toast.makeText(this, "No Location", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveLocation(location: Location) {
        dbReference.setValue(location)
    }


}