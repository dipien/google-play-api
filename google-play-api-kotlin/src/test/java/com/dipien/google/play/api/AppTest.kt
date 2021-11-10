package com.dipien.google.play.api

import org.junit.Assert
import org.junit.Test

class AppTest {
    @Test
    fun testUserFraction() {
        var app = App()
        app.userFraction = 0.001
        Assert.assertEquals(0.1, app.userPercentage!!, 0.0)
        app = App()
        app.userFraction = 0.01
        Assert.assertEquals(1.0, app.userPercentage!!, 0.0)
        app = App()
        app.userFraction = 0.1
        Assert.assertEquals(10.0, app.userPercentage!!, 0.0)
        app = App()
        app.userFraction = 0.5
        Assert.assertEquals(50.0, app.userPercentage!!, 0.0)
    }

    @Test
    fun testUserPercentage() {
        var app = App()
        app.userPercentage = 0.1
        Assert.assertEquals(0.001, app.userFraction!!, 0.0)
        app = App()
        app.userPercentage = 1.0
        Assert.assertEquals(0.01, app.userFraction!!, 0.0)
        app = App()
        app.userPercentage = 10.0
        Assert.assertEquals(0.1, app.userFraction!!, 0.0)
        app = App()
        app.userPercentage = 50.0
        Assert.assertEquals(0.5, app.userFraction!!, 0.0)
    }
}
