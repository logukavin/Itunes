package com.sample.itunes.extensions

import androidx.appcompat.widget.AppCompatImageView
import coil.load
import com.sample.itunes.R

fun AppCompatImageView.loadUrl(url: String?) {
    if (url.isNullOrEmpty()) {
        this.setImageResource(R.drawable.placeholder_for_missing_posters)
    } else {
        this.load(url) {
            placeholder(R.drawable.placeholder_for_missing_posters)
            error(R.drawable.placeholder_for_missing_posters)
        }
    }

}





