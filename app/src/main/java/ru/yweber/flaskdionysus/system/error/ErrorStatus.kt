package ru.yweber.flaskdionysus.system.error

/**
 * Created on 14.04.2020
 * @author YWeber */

sealed class ErrorStatus {
    object Unavailable : ErrorStatus()
    object Unknown : ErrorStatus()
}

