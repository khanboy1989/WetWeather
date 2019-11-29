package com.jayway.techtest.wetweather.models

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherApi {

    @GET
    fun getCurrentWeather(@Url url:String):Single<CurrentWeather>
}