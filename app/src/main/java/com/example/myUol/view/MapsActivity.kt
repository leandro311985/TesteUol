package com.example.myUol.view

import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myUol.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.myUol.model.Points
import com.example.myUol.presenter.showToast
import com.example.myUol.view.MainActivity.Companion.POINT
import com.google.android.gms.location.LocationServices

class MapsActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    OnMapReadyCallback {


    private var points: Points? = null
    private lateinit var lastLocation: Location
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)



        if (intent != null) {
            points = intent.getParcelableExtra(POINT)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        mMap.isTrafficEnabled = false
        mMap.isIndoorEnabled = false
        mMap.isBuildingsEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true

        setUpMap()
        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
        points?.let {
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(it.Latitude.toDouble(), it.Longitude.toDouble()))
                    .title(it.Descricao)


            )
        }


    }

    private fun setUpMap() {
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }



    override fun onMyLocationClick(location: Location) {
        location.latitude
        showToast("localização atual:\n$location")
    }

    override fun onMyLocationButtonClick(): Boolean {
        showToast("Recentralizando")

        return false
    }


}




