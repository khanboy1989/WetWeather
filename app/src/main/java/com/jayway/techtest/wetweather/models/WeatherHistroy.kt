package com.jayway.techtest.wetweather.models

import android.os.Parcel
import android.os.Parcelable
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

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(WeatherMain::class.java.classLoader),
        parcel.createTypedArrayList(Weather),
        parcel.readParcelable(Clouds::class.java.classLoader),
        parcel.readParcelable(Wind::class.java.classLoader),
        parcel.readParcelable(Sys::class.java.classLoader),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dt)
        parcel.writeParcelable(main, flags)
        parcel.writeTypedList(weather)
        parcel.writeParcelable(clouds, flags)
        parcel.writeParcelable(wind, flags)
        parcel.writeParcelable(sys, flags)
        parcel.writeString(dt_text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherData> {
        override fun createFromParcel(parcel: Parcel): WeatherData {
            return WeatherData(parcel)
        }

        override fun newArray(size: Int): Array<WeatherData?> {
            return arrayOfNulls(size)
        }
    }
}

