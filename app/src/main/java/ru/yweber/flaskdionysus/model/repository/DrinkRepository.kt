package ru.yweber.flaskdionysus.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.weber.proto.DrinksRequest
import ru.yweber.flaskdionysus.model.client.GrpcConnectClient
import ru.yweber.flaskdionysus.model.entity.DrinkEntity
import ru.yweber.flaskdionysus.model.entity.DrinksEntity
import ru.yweber.flaskdionysus.system.error.ErrorStatusSender
import timber.log.Timber
import javax.inject.Inject

/**
 * Created on 14.04.2020
 * @author YWeber */

class DrinkRepository @Inject constructor(
    private val client: GrpcConnectClient,
    private val errorStatus: ErrorStatusSender
) {
    fun pageDrink(page: Int) = flow<DrinksEntity> {
        val startPage = DrinksRequest.newBuilder()
            .setPage(page)
            .build()

        val onePageDrinks = client.getDrinkList(startPage).drinksList
            .map {
                Timber.e(it.mark.toString())
                DrinkEntity(
                    it.id, it.name,
                    it.icon, it.mark.toInt(),
                    it.isFlacky, it.isFire,
                    it.isIba, it.properties, it.ingredients
                )
            }
        emit(DrinksEntity.Result(onePageDrinks))
    }.catch {
        emit(DrinksEntity.Error(errorStatus.sender(it)))
    }.flowOn(Dispatchers.IO)
}