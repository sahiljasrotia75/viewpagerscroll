package com.app.atsz7.viewpagerautoscroll

import android.app.Application
import com.app.atsz7.viewpagerautoscroll.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object{
        var mInstance: App? = null
        fun getInstance() : App?{
            return mInstance
        }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {   // 1
            androidContext(applicationContext)  // 3
            modules(listOf(networkModule, viewModelModule))  // 4
        }
        mInstance = this
    }

}
