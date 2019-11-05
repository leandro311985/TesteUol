package com.example.myUol.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.View
import android.widget.Toast

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


