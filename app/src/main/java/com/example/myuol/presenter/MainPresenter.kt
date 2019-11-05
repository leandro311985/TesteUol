package com.example.myuol.presenter

import android.content.Context
import android.location.Location
import com.example.myuol.inteface.Constant
import com.example.myuol.model.Points

class MainPresenter(
    private var mainView: Constant?,
    private val findItemsInteractor: ItemsInteractor,
    private val context: Context
) {

    fun onResume(locationUser: Location) {
        mainView?.showProgress()
        findItemsInteractor.findItems(context, locationUser, ::onItemsLoaded)
    }

    private fun onItemsLoaded(items: List<Points>?) {
        if (items != null) {
            mainView?.apply {
                setItems(items)
                hideProgress()
            }
        }
    }

    fun onItemClicked(item: Points) {
        mainView?.showMessage(item)
    }

    fun onDestroy() {
        mainView = null
    }
}


