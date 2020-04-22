package ru.yweber.flaskdionysus.system

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

/**
 * Created on 22.04.2020
 * @author YWeber */

class ResourceManager @Inject constructor(private val context: Context) {
    fun getString(@StringRes stringId: Int) = context.getString(stringId)
}