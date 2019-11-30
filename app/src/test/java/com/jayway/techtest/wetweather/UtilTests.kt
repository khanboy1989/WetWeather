package com.jayway.techtest.wetweather

import com.jayway.techtest.wetweather.util.convertToDateTime
import org.junit.Assert
import org.junit.Test

class UtilTests {

    @Test
    fun testUtilConvertTimeStampToDate(){
        val timeStampDate = "1575040157".convertToDateTime()
        Assert.assertEquals(timeStampDate,"29 Nov 2019")
    }
}