package com.company.go

import android.app.Application

class GoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize application components
        initializeApp()
    }

    private fun initializeApp() {
        // TODO: Initialize sensors, repositories, etc.
    }
}
