package ru.yweber.flaskdionysus.model.client

import ru.weber.proto.DictionariesResponse
import ru.weber.proto.DrinkOfDayResponse
import ru.weber.proto.DrinksRequest
import ru.weber.proto.DrinksResponse

/**
 * Created on 14.04.2020
 * @author YWeber */

interface GrpcConnectClient {
    suspend fun getDrinkList(request: DrinksRequest): DrinksResponse
    suspend fun getDrinkDay(): DrinkOfDayResponse
    suspend fun getFilters(): DictionariesResponse
}