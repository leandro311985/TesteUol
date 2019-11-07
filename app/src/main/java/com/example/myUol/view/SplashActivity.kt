package com.example.myUol.view

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.myUol.BuildConfig
import com.example.myUol.R
import com.example.myUol.presenter.showSnackBar
import com.example.myUol.presenter.openActivity
import com.example.myUol.presenter.schedule
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.Executors

class SplashActivity : AppCompatActivity() {

    private lateinit var textsplash: LinearLayoutCompat
    private lateinit var texthome: LinearLayoutCompat
    private lateinit var menus: LinearLayoutCompat
    private lateinit var frombottom: Animation
    private lateinit var rocketAnimation: AnimationDrawable


    companion object {
        private var mDelayHandler: Handler? = null
        private const val SPLASHDELAY: Long = 5000
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
        animation()
         imageId.apply {
            setBackgroundResource(R.drawable.animation)
            rocketAnimation = background as AnimationDrawable
        }
        version.text = "V.".plus(BuildConfig.VERSION_NAME)
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

    private fun snack(){
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
   private fun animation(){
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom)
        textsplash = findViewById<View>(R.id.textsplash) as LinearLayoutCompat
        texthome = findViewById<View>(R.id.texthome) as LinearLayoutCompat
        menus = findViewById<View>(R.id.menus) as LinearLayoutCompat

        bgapp.animate().translationY(-1900f).setDuration(3000).startDelay = 600
        clover.animate().alpha(0f).setDuration(3000).startDelay = 600
        textsplash.animate().translationY(140f).alpha(0f).setDuration(900).startDelay = 600

        texthome.startAnimation(frombottom)
        menus.startAnimation(frombottom)
    }
}
