package com.jayway.techtest.wetweather.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp


data class CurrentWeather(

    @SerializedName("list")
    @Expose
    var list :List<CurrentWeatherInfo>?

)


data class CurrentWeatherInfo(

    @SerializedName("id")
    var id:Long?,

    @SerializedName("name")
    var name:String?,

    @SerializedName("dt")
    var dt:Long?,

    @SerializedName("rain")
    var rain:String?,

    @SerializedName("snow")
    var snow:String?
)