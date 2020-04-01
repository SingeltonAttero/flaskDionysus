package ru.yweber.flaskdionysus

import android.app.Application
import ru.yweber.flaskdionysus.di.AppScope
import ru.yweber.flaskdionysus.di.module.appModule
import ru.yweber.flaskdionysus.di.module.navigationModule
import timber.log.Timber
import toothpick.Scope
import toothpick.ktp.KTP

/**
 * Created on 29.03.2020
 * @author YWeber */

class App : Application() {

    private lateinit var rootScope: Scope

    override fun onCreate() {
        super.onCreate()
        initTimber()
        rootScope = KTP.openScope(AppScope::class.java)
            .installModules(appModule(this), navigationModule())
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        rootScope.release()
    }
}