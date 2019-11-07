package com.example.myUol.view

import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myUol.R
import com.example.myUol.inteface.Constant
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.myUol.model.Points
import com.example.myUol.presenter.openActivity
import com.example.myUol.presenter.showSnackBar
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    OnMapReadyCallback {

    private var points: Points? = null
    private lateinit var lastLocation: Location
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.title_activity_maps)

        btn_draw_State.setOnClickListener {
        }

        if (intent != null) {
            points = intent.getParcelableExtra(Constant.POINT)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        setUpMap()
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.isTrafficEnabled = false
        mMap.isIndoorEnabled = false
        mMap.isBuildingsEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
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
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
        showSnackBar(map.requireView(),getString(R.string.location).plus(location.latitude.plus(location.longitude)))
    }

    override fun onMyLocationButtonClick(): Boolean {
        showSnackBar(map.requireView(),getString(R.string.recentraliza))

        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home
            -> {
               openActivity<MainActivity> {  }
                finishAffinity()
            }
            else -> {
            }
        }
        return true
    }

}






