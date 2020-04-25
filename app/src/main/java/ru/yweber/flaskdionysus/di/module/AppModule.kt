package ru.yweber.flaskdionysus.di.module

import android.content.Context
import io.grpc.ManagedChannel
import ru.yweber.flaskdionysus.BuildConfig
import ru.yweber.flaskdionysus.core.notifier.RetryErrorNotifier
import ru.yweber.flaskdionysus.di.provider.ChannelsProvider
import ru.yweber.flaskdionysus.model.client.GrpcApi
import ru.yweber.flaskdionysus.model.client.GrpcConnectClient
import ru.yweber.flaskdionysus.model.repository.FilterRepository
import ru.yweber.flaskdionysus.system.ResourceManager
import ru.yweber.flaskdionysus.system.error.AndroidErrorStatusSender
import ru.yweber.flaskdionysus.system.error.ErrorStatusSender
import toothpick.ktp.binding.module

/**
 * Created on 30.03.2020
 * @author YWeber */

fun appModule(context: Context) = module {
    bind(Context::class.java).toInstance(context)
    bind(ManagedChannel::class.java).toProviderInstance(ChannelsProvider(BuildConfig.ENDPOINT, BuildConfig.PORT))
    bind(GrpcConnectClient::class.java).to(GrpcApi::class.java).singleton()
    bind(ErrorStatusSender::class.java).to(AndroidErrorStatusSender::class.java).singleton()
    bind(RetryErrorNotifier::class.java).toInstance(RetryErrorNotifier())
    bind(ResourceManager::class.java).singleton()
    bind(FilterRepository::class.java).singleton()
}
