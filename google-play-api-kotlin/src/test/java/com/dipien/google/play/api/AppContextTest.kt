package com.dipien.google.play.api

import org.junit.Assert
import org.junit.Test

class AppContextTest {
    @Test
    fun testUserFraction() {
        var appContext = AppContext()
        appContext.userFraction = 0.001
        Assert.assertEquals(0.1, appContext.userPercentage!!, 0.0)
        appContext = AppContext()
        appContext.userFraction = 0.01
        Assert.assertEquals(1.0, appContext.userPercentage!!, 0.0)
        appContext = AppContext()
        appContext.userFraction = 0.1
        Assert.assertEquals(10.0, appContext.userPercentage!!, 0.0)
        appContext = AppContext()
        appContext.userFraction = 0.5
        Assert.assertEquals(50.0, appContext.userPercentage!!, 0.0)
    }

    @Test
    fun testUserPercentage() {
        var appContext = AppContext()
        appContext.userPercentage = 0.1
        Assert.assertEquals(0.001, appContext.userFraction!!, 0.0)
        appContext = AppContext()
        appContext.userPercentage = 1.0
        Assert.assertEquals(0.01, appContext.userFraction!!, 0.0)
        appContext = AppContext()
        appContext.userPercentage = 10.0
        Assert.assertEquals(0.1, appContext.userFraction!!, 0.0)
        appContext = AppContext()
        appContext.userPercentage = 50.0
        Assert.assertEquals(0.5, appContext.userFraction!!, 0.0)
    }
}