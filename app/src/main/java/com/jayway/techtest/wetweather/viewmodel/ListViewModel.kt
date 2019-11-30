package com.jayway.techtest.wetweather.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jayway.techtest.wetweather.di.components.DaggerViewModelComponent
import com.jayway.techtest.wetweather.di.modules.AppModule
import com.jayway.techtest.wetweather.models.CurrentWeather
import com.jayway.techtest.wetweather.models.WeatherApiService
import com.jayway.techtest.wetweather.models.WeatherHistory
import com.jayway.techtest.wetweather.util.Feeds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(application: Application):AndroidViewModel(application) {

    private var TAG:String = ListViewModel::class.java.simpleName
    private val disposable = CompositeDisposable()
    val currentWeather by lazy {MutableLiveData<CurrentWeather>()}
    val weatherHistory by lazy {MutableLiveData<WeatherHistory>()}
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy{ MutableLiveData<Boolean>()}

    @Inject
    lateinit var apiService:WeatherApiService

    init {
        DaggerViewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }

    fun refreshCurrentWeather(cityName:String){
        loading.value = true
        loadError.value = false
        getCurrentWeather(Feeds.createGeCurrentWeatherUrl(cityName))
    }

    private fun getCurrentWeather(url:String){
       disposable.add(apiService.getCurrentWeather(url).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribeWith(object:DisposableSingleObserver<CurrentWeather>(){
                override fun onSuccess(weather: CurrentWeather) {
                    Log.d(TAG,weather.toString())
                    currentWeather.value = weather
                    loadError.value = false
                    loading.value = false
                    getWeatherHistory(weather.list?.get(0)?.id)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    currentWeather.value = null
                    loadError.value = true
                    loading.value = false
                }
            }))
    }


    private fun getWeatherHistory(requestId:Long?){
        val url = Feeds.createWeatherHistoryUrl(requestId.toString(),"20")
        disposable.add(
            apiService.getWeatherHistroyForGivenDuration(url).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object :DisposableSingleObserver<WeatherHistory>(){
                    override fun onSuccess(weatherHist: WeatherHistory) {
                        Log.d(TAG,weatherHist.toString())
                        weatherHistory.value = weatherHist
                        loading.value = false
                        loadError.value = false
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        weatherHistory.value = null
                        loadError.value = true
                        loading.value = false
                    } }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}