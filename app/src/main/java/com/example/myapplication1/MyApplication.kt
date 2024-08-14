package com.example.myapplication1

import android.app.Application
import android.content.Context


class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

    val Context.appComponent: AppComponent
        get() = when (this) {
            is MyApplication -> appComponent
            else -> this.applicationContext.appComponent
        }
}