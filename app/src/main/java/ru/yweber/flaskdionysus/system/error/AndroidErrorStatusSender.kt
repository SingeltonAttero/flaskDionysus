package ru.yweber.flaskdionysus.system.error

import io.grpc.Status
import io.grpc.StatusRuntimeException
import javax.inject.Inject

/**
 * Created on 14.04.2020
 * @author YWeber */

class AndroidErrorStatusSender @Inject constructor() : ErrorStatusSender {
    override fun sender(throwable: Throwable): ErrorStatus {
        return when (throwable) {
            is StatusRuntimeException -> {
                when (throwable.status) {
                    Status.UNAVAILABLE -> ErrorStatus.Unavailable
                    else -> ErrorStatus.Unknown
                }
            }
            else -> {
                ErrorStatus.Unknown
            }
        }
    }
}