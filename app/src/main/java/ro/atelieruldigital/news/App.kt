package ro.atelieruldigital.news

import android.app.Application

import com.jakewharton.threetenabp.AndroidThreeTen



import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        Timber.d("App has initialized...")
    }
}


