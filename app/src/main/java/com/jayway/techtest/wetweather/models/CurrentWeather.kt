package com.jayway.techtest.wetweather.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CurrentWeather(
    @SerializedName("list")
    @Expose
    var list: List<CurrentWeatherInfo>?
)

data class CurrentWeatherInfo(

    @SerializedName("id")
    var id: Long?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("dt")
    var dt: Long?,


    @SerializedName("snow")
    var snow: String?,

    @SerializedName("main")
    @Expose
    var main: WeatherMain?,

    @SerializedName("wind")
    @Expose
    var wind: Wind?,

    @SerializedName("sys")
    @Expose
    var sys: Sys?,

    @SerializedName("clouds")
    @Expose
    var clouds: Clouds?,

    @SerializedName("weather")
    var weatherList:List<Weather>?

)

data class WeatherMain(

    @SerializedName("temp")
    var temp: Double?,

    @SerializedName("pressure")
    var pressure: Int?,

    @SerializedName("humidity")
    var humidity: Int?,

    @SerializedName("temp_min")
    var temp_min: Double?,

    @SerializedName("temp_max")
    var temp_max: Double,

    @SerializedName("sea_level")
    var sea_level:Int?,

    @SerializedName("grand_level")
    var grand_level:Int?
)

data class Wind(
    @SerializedName("speed")
    var speed:Double?,

    @SerializedName("deg")
    var deg:Int?
)

data class Sys(
    @SerializedName("country")
    var country:String?,

    @SerializedName("pod")
    var pod:String?
)

data class Clouds (

    @SerializedName("all")
    var all:String?

)


data class Weather(

    @SerializedName("id")
    var id: Long?,

    @SerializedName("main")
    var main:String?,

    @SerializedName("description")
    var description:String?,

    @SerializedName("icon")
    var icon:String?
)