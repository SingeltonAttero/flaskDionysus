package ru.yweber.flaskdionysus.system.error

/**
 * Created on 14.04.2020
 * @author YWeber */

interface ErrorStatusSender {
    fun sender(throwable: Throwable): ErrorStatus
}