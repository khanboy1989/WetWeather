package com.jayway.techtest.wetweather.viewmodel

import android.app.Application
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

    //Inject the apiservice from dagger AppModule and ViewModel Component
    @Inject
    lateinit var apiService:WeatherApiService

    init {
        DaggerViewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }

    /**
     * the entry point function for getting current weather
     *
     * @param cityName where it is provided by WeatherListFragment search result.
     */
    fun refreshCurrentWeather(cityName:String){
        loading.value = true
        loadError.value = false
        currentWeather.value = null
        getCurrentWeather(Feeds.createGeCurrentWeatherUrl(cityName))
    }

    /**
     * gets the current weather depending on users searched city and country
     */
    private fun getCurrentWeather(url:String){
       disposable.add(apiService.getCurrentWeather(url).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribeWith(object:DisposableSingleObserver<CurrentWeather>(){
                override fun onSuccess(weather: CurrentWeather) {
                    currentWeather.value = weather
                    loadError.value = false
                    loading.value = false
                    getWeatherHistory(weather.list?.get(0)?.id)
                }

                override fun onError(e: Throwable) {
                    currentWeather.value = null
                    loadError.value = true
                    loading.value = false
                }
            }))
    }

    /**
     * Gets the forecast weather information for provided current weather request id
     * */
     fun getWeatherHistory(requestId:Long?){
        val url = Feeds.createWeatherHistoryUrl(requestId.toString(),"20")
        disposable.add(
            apiService.getWeatherHistroyForGivenDuration(url).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object :DisposableSingleObserver<WeatherHistory>(){
                    override fun onSuccess(weatherHist: WeatherHistory) {
                        weatherHistory.value = weatherHist
                        loading.value = false
                        loadError.value = false
                    }
                    override fun onError(e: Throwable) {
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