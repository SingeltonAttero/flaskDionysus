package ru.yweber.flaskdionysus.model.client

import io.grpc.ManagedChannel
import ru.weber.proto.*
import javax.inject.Inject

/**
 * Created on 14.04.2020
 * @author YWeber */

class GrpcApi @Inject constructor(private val messageChannel: ManagedChannel) : GrpcConnectClient {
    override suspend fun getDrinkList(request: DrinksRequest): DrinksResponse {
        val drinksStub = DrinksGrpc.newBlockingStub(messageChannel)
        return drinksStub.getDrinks(request)
    }

    override suspend fun getDrinkDay(): DrinkOfDayResponse {
        val drinkDayStub = DrinksGrpc.newBlockingStub(messageChannel)
        val empty = Empty.newBuilder().build()
        return drinkDayStub.getDrinkOfDay(empty)
    }

}