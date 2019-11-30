package com.jayway.techtest.wetweather.util

object Feeds {

    private const val find = "find?q="
    private const val type= "&type=like&"
    private const val units = "units=metric&"
    private const val appId = "appid=b4af92bbda4d0b6e66744b4df7fe1422"
    private const val forecast ="forecast?id="
    private const val count = "&cnt="

    fun createGeCurrentWeatherUrl(cityName:String):String{
        return "$find$cityName$type$units$appId"
    }

    fun createWeatherHistoryUrl(requestId:String,cnt:String):String{
        return "$forecast$requestId$count$cnt&$appId"
    }

}