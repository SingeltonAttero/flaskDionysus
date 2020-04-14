package ru.yweber.flaskdionysus.model.client

import io.grpc.ManagedChannel
import ru.weber.proto.DrinksGrpc
import ru.weber.proto.DrinksRequest
import ru.weber.proto.DrinksResponse
import javax.inject.Inject

/**
 * Created on 14.04.2020
 * @author YWeber */

class GrpcApi @Inject constructor(private val messageChannel: ManagedChannel) : GrpcConnectClient {
    override suspend fun getDrinkList(request: DrinksRequest): DrinksResponse {
        val drinksStub = DrinksGrpc.newBlockingStub(messageChannel)
        return drinksStub.getDrinks(request)
    }
}