package com.example.myUol.inteface

import com.example.myUol.model.Points

interface Constant {

    fun showProgress()
    fun hideProgress()
    fun setItems(items: List<Points>)
    fun showMessage(message: Points)

    companion object {
        const val REQUESTCODE = 124
        const val DELAY: Long = 3000
        const val POINT: String = "POINT"
        const val SPLASHDELAY: Long = 4000

    }



}