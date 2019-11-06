package com.example.myUol.view

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myUol.R
import com.example.myUol.inteface.Constant
import com.example.myUol.model.Points
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.animation.ValueAnimator
import androidx.core.app.ActivityCompat
import android.location.LocationManager
import android.location.Location
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.widget.Button
import com.example.myUol.presenter.*
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import kotlinx.android.synthetic.main.text_row_item.*

class MainActivity : AppCompatActivity(), Constant {

    private lateinit var presenter: MainPresenter
    private var mDelayHandler: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, ItemsInteractor(), this)
        showProgressRight(button = btnMaps)
        rv_list.layoutManager = LinearLayoutManager(this)
        permission()

    }


    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            openActivity<MapsActivity> { }
        }
    }

    override fun onPostResume() {
        super.onPostResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()

    }

    override fun showProgress() {
        progressBar.SHOW()
        rv_list.HIDE()
    }

    override fun hideProgress() {
        progressBar.HIDE()
        rv_list.SHOW()
    }

    override fun setItems(items: List<Points>) {
        rv_list.adapter = MainAdapter(items, presenter::onItemClicked)

    }

    override fun showMessage(message: Points) {
        showToast(message.Descricao)
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra(POINT, message)
        startActivity(intent)
    }

    private fun showProgressRight(button: Button) {
        btnMaps.setOnClickListener {
            btnMaps.showProgress {
                buttonTextRes = R.string.title_button
                progressColor = Color.WHITE
            }
            button.isEnabled = false
            Handler().postDelayed({
                button.isEnabled = true
                button.hideProgress(R.string.title_activity_maps)
            }, DELAY)

            mDelayHandler = Handler()
            mDelayHandler!!.postDelayed(
                mRunnable,
                SPLASHDELAY
            )

        }

    }

    fun permission() {
        if (ContextCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION
                ),
                REQUESTCODE
            )
        } else {
            getLastBestLocation()?.let {
                presenter.onResume(it)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastBestLocation(): Location? {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        var gpsLocationTime: Long = 0
        if (null != locationGPS) {
            gpsLocationTime = locationGPS.time
        }

        var netLocationTime: Long = 0

        if (null != locationNet) {
            netLocationTime = locationNet.time
        }

        return if (0 < gpsLocationTime - netLocationTime) {
            locationGPS
        } else {
            locationNet
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUESTCODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastBestLocation()?.let {
                    presenter.onResume(it) }
            }
        }
    }

    companion object {

        private const val REQUESTCODE = 124
        private const val DELAY: Long = 3000
        const val POINT: String = "POINT"
        private const val SPLASHDELAY: Long = 4000

    }

}


