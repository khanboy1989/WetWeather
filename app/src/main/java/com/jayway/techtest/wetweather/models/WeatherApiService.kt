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

    fun getCurrentWeather():Single<CurrentWeather>{
        return weatherApi.getCurrentWeather("find?q=London,GB&type=like&units=metric&appid=b4af92bbda4d0b6e66744b4df7fe1422")
    }
}