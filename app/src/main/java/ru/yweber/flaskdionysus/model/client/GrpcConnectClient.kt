package ru.yweber.flaskdionysus.model.client

import ru.weber.proto.*

/**
 * Created on 14.04.2020
 * @author YWeber */

interface GrpcConnectClient {
    suspend fun getDrinkList(request: DrinksRequest): DrinksResponse
    suspend fun getDrinkDay(): DrinkOfDayResponse
    suspend fun getFilters(): DictionariesResponse
    suspend fun getDetailedDrink(drinkId: Int): DrinkResponse
    suspend fun searchComponent(search: String, page: Int): NameResponse
}