package com.jayway.techtest.wetweather.di.components

import com.jayway.techtest.wetweather.di.modules.ApiModule
import com.jayway.techtest.wetweather.models.WeatherApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service:WeatherApiService)
}