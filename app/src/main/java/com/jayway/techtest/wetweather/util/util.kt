package com.jayway.techtest.wetweather.util

import com.google.android.libraries.places.api.model.Place
import java.text.SimpleDateFormat
import java.util.*


const val timeStampFormat:String = "dd/MM/yyyy"

fun Place.extractCityNameShortCountryName():String?{
    this.name?.let {
        return this.name + ","+ this.addressComponents?.asList()?.last()?.shortName
    }
    return null
}


fun String.convertToDateTime():String?{
    return try{
        val sdf = SimpleDateFormat(timeStampFormat)
        val netDate = Date(this.toLong() * 1000)
        sdf.format(netDate)
    }catch(e:Exception) {
        e.printStackTrace()
        null
    }
}