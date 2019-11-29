package com.jayway.techtest.wetweather.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app:Application) {

    @Provides
    fun providesApp():Application = app

}