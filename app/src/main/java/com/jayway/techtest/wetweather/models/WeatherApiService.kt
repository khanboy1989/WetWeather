package com.jayway.techtest.wetweather.models

import com.jayway.techtest.wetweather.di.components.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class WeatherApiService {

    @Inject
    lateinit var weatherApi:WeatherApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCurrentWeather(url:String):Single<CurrentWeather>{
        return weatherApi.getCurrentWeather(url)
    }

    fun getWeatherHistroyForGivenDuration(url:String):Single<WeatherHistory>{
        return weatherApi.getWeatherHistoryForGivenDuration(url)
    }
}