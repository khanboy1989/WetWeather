package com.jayway.techtest.wetweather.di

import com.jayway.techtest.wetweather.di.modules.ApiModule
import com.jayway.techtest.wetweather.models.WeatherApiService

class ApiModuleTest(val mockService:WeatherApiService): ApiModule() {


    override fun provideApiService(): WeatherApiService {
        return mockService
    }

}