package ru.yweber.flaskdionysus.di.provider

import io.grpc.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Provider

/**
 * Created on 14.04.2020
 * @author YWeber */

class ChannelsProvider(
    private val endpoint: String,
    private val port: Int
) : Provider<ManagedChannel> {
    override fun get(): ManagedChannel {
        return ManagedChannelBuilder.forAddress(endpoint, port)
            .usePlaintext()
            .idleTimeout(30, TimeUnit.SECONDS)
            .keepAliveTimeout(10, TimeUnit.SECONDS)
            .build()
    }
}