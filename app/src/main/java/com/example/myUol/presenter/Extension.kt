package com.example.myUol.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.myUol.R
import com.example.myUol.R.color.colorWhite
import com.google.android.material.snackbar.Snackbar
import java.util.*
import java.util.concurrent.Executors.callable
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

inline fun <reified T> Context.openActivity(
    clearTesk: Boolean = false,
    customExtraTag: String? = null,
    noinline  extras :(Bundle.() -> Unit)? = null
){
    Intent(this, T::class.java).apply {
        clearTesk thenDo {
            addFlags(FLAG_ACTIVITY_CLEAR_TASK or  FLAG_ACTIVITY_NEW_TASK)
        }
        extras?.also {
            putExtra(customExtraTag?:"moving on to another activity",Bundle().apply { extras::invoke })
        }
    }.also { startActivity(it) }
}

inline fun <reified T: Activity> Activity.openActivityForResult(requestCode: Int) {
    val intent = Intent(this, T::class.java)
    startActivityForResult(intent, requestCode)

}

infix fun Boolean.thenDo(action: () -> Unit){
    if (this)action()
}

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}
fun View.SHOW() {
    this.visibility = View.VISIBLE
}
fun View.HIDE() {
    this.visibility = View.INVISIBLE
}

fun Snackbar.config(context: Context){
    val params = this.view.layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(12, 20, 12, 20)
    this.view.layoutParams = params

    this.view.background = ContextCompat.getDrawable(context, R.drawable.snack_bottom)

    ViewCompat.setElevation(this.view, 20f)
}

@SuppressLint("ResourceAsColor")
fun Context.ShowSnackBar(view: View, text: CharSequence, duration: Int = Snackbar.LENGTH_LONG){
    val snack = Snackbar.make(view,text, duration)
    snack.setActionTextColor(colorWhite)
    snack.config(this)
    snack.show()
}

fun <V, T: ScheduledExecutorService> T.schedule(
    delay: Long,
    unit: TimeUnit = TimeUnit.MILLISECONDS,
    action: () -> V): ScheduledFuture<Any> {
    return this.schedule(
        callable { action() },
        delay, unit)
}

