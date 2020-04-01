package ru.yweber.flaskdionysus.di.module

import android.content.Context
import ru.yweber.flaskdionysus.di.utils.ToothpickViewModelFactory
import toothpick.ktp.binding.module

/**
 * Created on 30.03.2020
 * @author YWeber */

fun appModule(context: Context) = module {
    bind(Context::class.java).toInstance(context)
}
