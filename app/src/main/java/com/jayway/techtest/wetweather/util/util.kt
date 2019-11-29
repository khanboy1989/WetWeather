package com.jayway.techtest.wetweather.util

import com.google.android.libraries.places.api.model.Place


fun Place.extractCityNameShortCountryName():String?{
    this.name?.let {
        return this.name + ","+ this.addressComponents?.asList()?.last()?.shortName
    }
    return null
}