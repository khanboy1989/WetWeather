package com.jayway.techtest.wetweather

import com.jayway.techtest.wetweather.util.Feeds
import org.junit.Assert
import org.junit.Test


class FeedsTest{

    @Test
    fun createUrlForCurrentWeatherInfo(){
        val url = Feeds.createGeCurrentWeatherUrl("Malmö,Sweden")
        Assert.assertEquals(url ,"find?q=Malmö,Sweden&type=like&units=metric&appid=b4af92bbda4d0b6e66744b4df7fe1422")

    }


    @Test
    fun createUrlForWeatherHistory(){
        val url = Feeds.createWeatherHistoryUrl("100","7")
        Assert.assertEquals(url,"forecast?id=100&cnt=7&units=metric&appid=b4af92bbda4d0b6e66744b4df7fe1422")
    }
}