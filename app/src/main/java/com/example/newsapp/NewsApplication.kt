package com.example.newsapp

import android.app.Application
import com.example.newsapp.util.TypefaceUtil
import timber.log.Timber

class NewsApplication : Application() {
    companion object {
        private var mInstance: NewsApplication? = null
        fun getInstance(): NewsApplication? {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/Roboto-Regular.ttf");
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}