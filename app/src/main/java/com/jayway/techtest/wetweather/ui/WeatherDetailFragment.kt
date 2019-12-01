package com.jayway.techtest.wetweather.ui


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jayway.techtest.wetweather.R
import com.jayway.techtest.wetweather.models.WeatherData
import com.jayway.techtest.wetweather.util.convertToDateTime
import com.jayway.techtest.wetweather.util.loadIcon
import kotlinx.android.synthetic.main.fragment_weather_detail.view.*
import kotlin.math.roundToInt


class WeatherDetailFragment : Fragment() {

    private lateinit var rootView:View
    private var weatherData:WeatherData? = null
    private var cityName:String ? = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_weather_detail, container, false)
        getWeatherData()
        return rootView
    }

    private fun getWeatherData(){
        arguments?.let {
            weatherData = WeatherDetailFragmentArgs.fromBundle(it).weatherDataItem
            cityName = WeatherDetailFragmentArgs.fromBundle(it).cityName
            setViewData(weatherData,cityName)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setViewData(item:WeatherData?,cityName:String?){
        rootView.dateTimeTextView.text = item?.dt?.convertToDateTime()
        rootView.statusImageView.loadIcon(item?.weather?.get(0)?.icon)
        rootView.mainWeatherDescriptionTV.text = item?.weather?.get(0)?.main
        rootView.temperatureTV.text = item?.main?.temp?.roundToInt().toString() + getString(R.string.temp_symbol)
        rootView.pressureTV.text = item?.main?.pressure.toString() + " " + getString(R.string.pressure_unit)
        rootView.humidityTV.text = item?.main?.humidity?.toString() + " " + getString(R.string.percentage)
        rootView.windTV.text = item?.wind?.speed.toString() + " " + getString(R.string.wind_speed_symbol)
        rootView.cityNameTV.text = this.cityName
    }

}
