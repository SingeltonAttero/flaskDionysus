package ru.yweber.flaskdionysus.di.utils

/**
 * Created on 06.05.2020
 * @author YWeber */

data class PrimitiveWrapper<out T>(val value: T)  // see: https://youtrack.jetbrains.com/issue/KT-18918
