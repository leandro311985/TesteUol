package com.example.myUol.inteface

import com.example.myUol.model.Points

interface Constant {

    fun showProgress()
    fun hideProgress()
    fun setItems(items: List<Points>)
    fun showMessage(message: Points)


}