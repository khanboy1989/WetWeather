package com.jayway.techtest.wetweather.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.libraries.places.api.model.Place
import com.jayway.techtest.wetweather.R
import java.text.SimpleDateFormat
import java.util.*


fun Place.extractCityNameShortCountryName(): String? {
    this.name?.let {
        return this.name + "," + this.addressComponents?.asList()?.last()?.shortName
    }
    return null
}


fun String.convertToDateTime(): String? {
    return try {
        val timeStampFormat: String = "dd MMM yyyy HH:mm"
        val sdf = SimpleDateFormat(timeStampFormat)
        val netDate = Date(this.toLong() * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getProgresDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions.placeholderOf(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

fun ImageView.loadIcon(icon: String) {
    Glide.with(context)
        .load("http://openweathermap.org/img/w/$icon.png',")
        .into(this)
}