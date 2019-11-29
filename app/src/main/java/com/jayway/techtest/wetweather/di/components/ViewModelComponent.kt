package com.jayway.techtest.wetweather.di.components

import com.jayway.techtest.wetweather.di.modules.ApiModule
import com.jayway.techtest.wetweather.di.modules.AppModule
import com.jayway.techtest.wetweather.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class,AppModule::class])
interface ViewModelComponent {
    fun inject(viewModel:ListViewModel)
}