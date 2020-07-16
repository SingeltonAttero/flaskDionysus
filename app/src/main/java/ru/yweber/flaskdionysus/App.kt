package ru.yweber.flaskdionysus

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.yweber.flaskdionysus.di.AppScope
import ru.yweber.flaskdionysus.di.module.appModule
import ru.yweber.flaskdionysus.di.module.navigationModule
import ru.yweber.flaskdionysus.model.client.CoilResponseHeaderInterceptor
import timber.log.Timber
import toothpick.Scope
import toothpick.ktp.KTP
import java.io.File

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
        initDefaultCoil()
    }

    private fun initDefaultCoil() {
        Coil.setDefaultImageLoader {
            ImageLoader(this) {
                componentRegistry {
                    add(SvgDecoder(this@App))
                        .build()
                }.okHttpClient {
                    val cacheDirectory = File(filesDir, "image_cache").apply {
                        mkdirs()
                    }
                    val cache = Cache(cacheDirectory, Long.MAX_VALUE)
                    val cacheControlInterceptor =
                        CoilResponseHeaderInterceptor("Cache-Control", "max-age=31536000,public")
                    OkHttpClient.Builder()
                        .cache(cache)
                        .addNetworkInterceptor(cacheControlInterceptor)
                        .addInterceptor(HttpLoggingInterceptor())
                        .build()
                }.build()
            }
        }
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