package com.example.myuol.view

import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myuol.R
import com.example.myuol.presenter.openActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    companion object {
        private var mDelayHandler: Handler? = null
        private const val SPLASHDELAY: Long = 5000
        private lateinit var rocketAnimation: AnimationDrawable
    }


    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            openActivity<MainActivity> {  }
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        

         imageId.apply {
            setBackgroundResource(R.drawable.animation)
            rocketAnimation = background as AnimationDrawable
        }

        rocketAnimation.start()

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable,
            SPLASHDELAY
        )

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
