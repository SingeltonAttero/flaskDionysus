package ru.yweber.flaskdionysus.system

import coil.request.LoadRequestBuilder
import timber.log.Timber

/**
 * Created on 20.04.2020
 * @author YWeber */

inline fun LoadRequestBuilder.finishLoadedCoil(crossinline finish: () -> Unit) {
    listener(onSuccess = { _, _ ->
        finish()
    }, onError = { _, exc ->
        Timber.e(exc)
        finish()
    })
}
