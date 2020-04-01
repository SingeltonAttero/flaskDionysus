package ru.yweber.flaskdionysus.di.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import toothpick.Toothpick

/**
 * Created on 30.03.2020
 * @author YWeber */


class ToothpickViewModelFactory(private val scopeName: Any) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(clazz: Class<T>): T {
        return Toothpick.openScope(scopeName).getInstance(clazz) as T
    }
}