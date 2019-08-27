package me.tatocaster.brandavatargeneratorsample

import android.app.Application
import timber.log.Timber

class BrandAvatarGeneratorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}