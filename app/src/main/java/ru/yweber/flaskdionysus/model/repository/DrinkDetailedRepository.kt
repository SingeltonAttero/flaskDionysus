package ru.yweber.flaskdionysus.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.yweber.flaskdionysus.model.client.GrpcConnectClient
import ru.yweber.flaskdionysus.model.entity.DrinkDetailedEntity
import ru.yweber.flaskdionysus.model.entity.IngredientEntity
import ru.yweber.flaskdionysus.model.entity.InstrumentEntity
import ru.yweber.flaskdionysus.system.absoluteImagePath
import javax.inject.Inject

/**
 * Created on 07.05.2020
 * @author YWeber */

class DrinkDetailedRepository @Inject constructor(private val api: GrpcConnectClient) {


    fun getDetailed(drinkId: Int): Flow<DrinkDetailedEntity> = flow {
        val detailedDrink = api.getDetailedDrink(drinkId)
        val entity = DrinkDetailedEntity(
            detailedDrink.id,
            detailedDrink.name,
            detailedDrink.nameEn,
            detailedDrink.mark,
            absoluteImagePath(detailedDrink.icon),
            detailedDrink.triedBy,
            detailedDrink.fortress,
            detailedDrink.complication,
            detailedDrink.isFire,
            detailedDrink.isFlacky,
            detailedDrink.isIba,
            detailedDrink.recipe,
            detailedDrink.instrumentsList.map {
                InstrumentEntity(it.id, it.name, absoluteImagePath(it.image))
            },
            detailedDrink.ingredientsList.map {
                IngredientEntity(it.id, it.name, it.volume)
            }
        )
        emit(entity)
    }.flowOn(Dispatchers.IO)
}