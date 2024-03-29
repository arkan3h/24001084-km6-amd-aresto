package com.arkan.aresto

import android.app.Application
import com.arkan.aresto.data.source.local.database.AppDatabase

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}