package ru.yweber.flaskdionysus.model.client

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created on 20.04.2020
 * @author YWeber */

class CoilResponseHeaderInterceptor(
    private val name: String,
    private val value: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder().header(name, value).build()
    }
}