package com.jayway.techtest.wetweather.di.modules

import com.jayway.techtest.wetweather.models.WeatherApi
import com.jayway.techtest.wetweather.models.WeatherApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {

    private val BASE_URL = "http://api.openweathermap.org/data/2.5/"

    @Provides
    fun provideWeatherApi():WeatherApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    open fun provideApiService():WeatherApiService{
        return WeatherApiService()
    }

}