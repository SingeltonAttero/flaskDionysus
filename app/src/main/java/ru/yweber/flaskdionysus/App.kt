package ru.yweber.flaskdionysus

import android.app.Application
import leakcanary.LeakCanary
import ru.weber.proto.DictionariesProto
import ru.weber.proto.DictionariesRequest
import ru.weber.proto.DictionariesResponse
import ru.weber.proto.DrinkRequest
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
        LeakCanary.showLeakDisplayActivityLauncherIcon(true)
        initTimber()
        rootScope = KTP.openScope(AppScope::class.java)
            .installModules(appModule(this), navigationModule())
    }

    private fun initTimber() {
        val builder = DrinkRequest
            .newBuilder()
            .setId(1)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        rootScope.release()
    }
}