package com.jayway.techtest.wetweather.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherHistory (

    @SerializedName("list")
    @Expose
    var historyList:List<WeatherData>?
)


data class WeatherData(

    @SerializedName("dt")
    var dt:String?,

    @SerializedName("main")
    var main:WeatherMain?,

    @SerializedName("weather")
    @Expose
    var weather:List<Weather>?,

    @SerializedName("clouds")
    var clouds:Clouds?,

    @SerializedName("wind")
    var wind:Wind?,

    @SerializedName("sys")
    var sys:Sys?,

    @SerializedName("dt_txt")
    var dt_text:String?

)

