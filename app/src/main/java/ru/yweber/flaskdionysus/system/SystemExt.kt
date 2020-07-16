package ru.yweber.flaskdionysus.system

import ru.yweber.flaskdionysus.BuildConfig

/**
 * Created on 07.05.2020
 * @author YWeber */

fun absoluteImagePath(localPath: String) =
    "http://${BuildConfig.ENDPOINT}:9001${localPath}"