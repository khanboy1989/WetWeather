package com.jayway.techtest.wetweather.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.jayway.techtest.wetweather.di.components.DaggerViewModelComponent
import com.jayway.techtest.wetweather.di.modules.AppModule
import com.jayway.techtest.wetweather.models.CurrentWeather
import com.jayway.techtest.wetweather.models.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(application: Application):AndroidViewModel(application) {

    private var TAG:String = ListViewModel::class.java.simpleName

    @Inject
    lateinit var apiService:WeatherApiService

    init {
        DaggerViewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }

    @SuppressLint("CheckResult")
    fun getCurrentWeather(){
        apiService.getCurrentWeather().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribeWith(object:DisposableSingleObserver<CurrentWeather>(){
                override fun onSuccess(t: CurrentWeather) {
                    Log.d(TAG,t.list?.get(0)?.dt.toString())
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG,e.localizedMessage)
                }
            })

    }

}