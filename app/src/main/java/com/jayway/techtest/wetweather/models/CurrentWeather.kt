package com.jayway.techtest.wetweather.models

import android.os.Parcel
import android.os.Parcelable
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
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readDouble(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(temp)
        parcel.writeValue(pressure)
        parcel.writeValue(humidity)
        parcel.writeValue(temp_min)
        parcel.writeDouble(temp_max)
        parcel.writeValue(sea_level)
        parcel.writeValue(grand_level)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherMain> {
        override fun createFromParcel(parcel: Parcel): WeatherMain {
            return WeatherMain(parcel)
        }

        override fun newArray(size: Int): Array<WeatherMain?> {
            return arrayOfNulls(size)
        }
    }
}

data class Wind(
    @SerializedName("speed")
    var speed:Double?,

    @SerializedName("deg")
    var deg:Int?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(speed)
        parcel.writeValue(deg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Wind> {
        override fun createFromParcel(parcel: Parcel): Wind {
            return Wind(parcel)
        }

        override fun newArray(size: Int): Array<Wind?> {
            return arrayOfNulls(size)
        }
    }
}

data class Sys(
    @SerializedName("country")
    var country:String?,

    @SerializedName("pod")
    var pod:String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(country)
        parcel.writeString(pod)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sys> {
        override fun createFromParcel(parcel: Parcel): Sys {
            return Sys(parcel)
        }

        override fun newArray(size: Int): Array<Sys?> {
            return arrayOfNulls(size)
        }
    }
}

data class Clouds (

    @SerializedName("all")
    var all:String?

):Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(all)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clouds> {
        override fun createFromParcel(parcel: Parcel): Clouds {
            return Clouds(parcel)
        }

        override fun newArray(size: Int): Array<Clouds?> {
            return arrayOfNulls(size)
        }
    }
}


data class Weather(

    @SerializedName("id")
    var id: Long?,

    @SerializedName("main")
    var main:String?,

    @SerializedName("description")
    var description:String?,

    @SerializedName("icon")
    var icon:String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(main)
        parcel.writeString(description)
        parcel.writeString(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}