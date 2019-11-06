package com.example.myUol.view

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.myUol.R
import com.example.myUol.presenter.showSnackBar
import com.example.myUol.presenter.openActivity
import com.example.myUol.presenter.schedule
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.Executors

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
        snack()
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

    fun snack(){

        val scheduledExecutor = Executors.newScheduledThreadPool(1)
        try {
            scheduledExecutor.schedule(1000) {
                showSnackBar(txt,getString(R.string.txt_splash))
            }
            scheduledExecutor.schedule(3000) {
                showSnackBar(txt,getString(R.string.txt_splash_ss))
            }
        } finally {
            scheduledExecutor.shutdown()
        }

    }
}
