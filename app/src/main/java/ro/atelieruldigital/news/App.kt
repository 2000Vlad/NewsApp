package ro.atelieruldigital.news

import android.app.Application

import com.jakewharton.threetenabp.AndroidThreeTen

import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this)
        Timber.d("App has initialized...")
    }
}


