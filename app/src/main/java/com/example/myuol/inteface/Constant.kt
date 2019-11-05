package com.example.myuol.inteface

import com.example.myuol.model.Points

interface Constant {

    fun showProgress()
    fun hideProgress()
    fun setItems(items: List<Points>)
    fun showMessage(message: Points)


}