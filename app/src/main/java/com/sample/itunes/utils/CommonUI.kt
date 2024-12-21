package com.sample.itunes.utils

import android.content.Context
import android.view.View
import android.widget.Toast

object CommonUI {

    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun View.showVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.showGone() {
        this.visibility = View.GONE
    }
}