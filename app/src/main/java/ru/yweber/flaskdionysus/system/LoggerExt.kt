package ru.yweber.flaskdionysus.system

import timber.log.Timber

/**
 * @author YWeber */

inline fun <reified T : Any> T.printSimpleName() = apply { Timber.e(T::class.java.simpleName) }

