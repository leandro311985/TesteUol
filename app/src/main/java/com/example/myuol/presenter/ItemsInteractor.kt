package com.example.myuol.presenter

import android.content.Context
import android.location.Location
import com.example.myuol.model.Points
import com.google.gson.Gson
import java.io.IOException
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class ItemsInteractor {

    fun findItems(context: Context, locationUser: Location, callback: (List<Points>?) -> Unit) {
         callback(loadJson(context, locationUser))
    }

    private fun loadJson(context: Context, locationUser: Location): List<Points>?{
        val gson = Gson()
        var points: List<Points>? = null
        try {

            val stream = context.assets.open("pontostest.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val contents = String(buffer)

            points = gson.fromJson(contents , Array<Points>::class.java).toList()

        }catch (e: IOException){

        }
        points?.let {

            for (point in it) {
                val location = Location("")
                location.latitude = point.Latitude.toDouble();
                location.longitude = point.Longitude.toDouble();
                point.distance = calculateDistance(locationUser, location).toFloat()
            }

            sort(it)
        }
        return points
    }

    fun sort( list: List<Points>) {
        Collections.sort(list
        ) { o1, o2 -> o1.distance.compareTo(o2.distance) }
    }

    private fun calculateDistance(userLocation: Location, wifiLocation: Location): Double {

        val theta = userLocation.longitude - wifiLocation.longitude
        var dist = sin(deg2rad(userLocation.latitude)) * sin(deg2rad(wifiLocation.latitude)) + (cos(deg2rad(userLocation.latitude))
                * cos(deg2rad(wifiLocation.latitude))
                * cos(deg2rad(theta)))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60.0 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}