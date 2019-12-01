package com.jayway.techtest.wetweather

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jayway.techtest.wetweather.di.ApiModuleTest
import com.jayway.techtest.wetweather.di.components.DaggerViewModelComponent
import com.jayway.techtest.wetweather.di.modules.AppModule
import com.jayway.techtest.wetweather.models.*
import com.jayway.techtest.wetweather.util.Feeds
import com.jayway.techtest.wetweather.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Callable
import java.util.concurrent.Executor


@RunWith(MockitoJUnitRunner::class)
class ListViewModelTests{

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var weatherService:WeatherApiService

    val application = Mockito.mock(Application::class.java)

    var listViewModel = ListViewModel(application)


   @Before
   fun setup(){
       MockitoAnnotations.initMocks(this)
       DaggerViewModelComponent.builder().appModule(AppModule(application)).
           apiModule(ApiModuleTest(weatherService)).build().inject(listViewModel)
   }

    @Before
    fun setupRxSchedulers(){
        val immidiate = object: Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() },true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler{ t: Callable<Scheduler> -> immidiate  }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { t: Callable<Scheduler> ->  immidiate}
    }

    /**
     * Test the getting current weather for given city success
     * */
    @Test
    fun getCurrentWeatherSuccess(){

        val list:MutableList<CurrentWeatherInfo> = mutableListOf()

        val weatherList = mutableListOf<Weather>()

        weatherList.add(Weather(1,"main","main","icon"))

        val item = CurrentWeatherInfo(1080,"name",10,"snow",
            WeatherMain(10.0,10,10,10.0,10.0,10,10),
            Wind(10.0,20),Sys(null,null), Clouds(null), weatherList)

        list.add(item)

        val currentWeather = CurrentWeather(list)

        val testSingle = Single.just(currentWeather)

        Mockito.`when`(weatherService.getCurrentWeather(Feeds.createGeCurrentWeatherUrl("London,UK"))).thenReturn(testSingle)
        Mockito.`when`(weatherService.getWeatherHistroyForGivenDuration(Feeds.createWeatherHistoryUrl("1080","20"))).thenReturn(Single.just(
            WeatherHistory(mutableListOf())))

        listViewModel.refreshCurrentWeather("London,UK")

        Assert.assertEquals(1, listViewModel.currentWeather.value?.list?.get(0)?.weatherList?.size)
        Assert.assertEquals(false,listViewModel.loadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)

    }


    /**
     * Test the getting current weather for given city failure case
     * */
    @Test
    fun getCurrentWeatherFailure(){
        val testSingle = Single.error<CurrentWeather>(Throwable("could not load the data"))

        Mockito.`when`(weatherService.getCurrentWeather(Feeds.createGeCurrentWeatherUrl("London,UK"))).thenReturn(testSingle)

        listViewModel.refreshCurrentWeather("London,UK")

        Assert.assertEquals(null,listViewModel.weatherHistory.value)
        Assert.assertEquals(true,listViewModel.loadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)

    }

    /**
     * Test the success outcome for weather history
     * */
    @Test
    fun getWeatherHistorySuccess() {

        val weatherData = mutableListOf<WeatherData>()
        val weatherList = mutableListOf<Weather>()

        weatherList.add(Weather(1, "main", "main", "icon"))

        val item = WeatherData(
            "dt",
            WeatherMain(10.0, 10, 10, 10.0, 10.0, 10, 10),
            weatherList,
            Clouds("all"),
            Wind(null, null),
            Sys(null, null), "dt_text"
        )

        weatherData.add(item)

        val weatherHistory = WeatherHistory(weatherData)

        val testSingle = Single.just(weatherHistory)

        Mockito.`when`(
            weatherService.getWeatherHistroyForGivenDuration(
                Feeds.createWeatherHistoryUrl(
                    "1080",
                    "20")
            )
        ).thenReturn(testSingle)

        listViewModel.getWeatherHistory(1080)

        Assert.assertEquals(
            1,
            listViewModel.weatherHistory.value?.historyList?.get(0)?.weather?.size
        )
        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(false, listViewModel.loadError.value)

    }


    /**
     * Test the get weather history for failure
     * */
    @Test
    fun getWeatherHistoryFailure(){

        val testSingle = Single.error<WeatherHistory>(Throwable("could not load data"))

        Mockito.`when`(weatherService.getWeatherHistroyForGivenDuration(Feeds.createWeatherHistoryUrl("1080","20"))).thenReturn(testSingle)

        listViewModel.getWeatherHistory(1080)

        Assert.assertEquals(null,listViewModel.weatherHistory.value)
        Assert.assertEquals(true,listViewModel.loadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)

    }
}