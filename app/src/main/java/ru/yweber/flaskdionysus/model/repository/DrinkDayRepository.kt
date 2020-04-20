package ru.yweber.flaskdionysus.model.repository

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import ru.yweber.flaskdionysus.model.client.GrpcConnectClient
import ru.yweber.flaskdionysus.model.entity.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created on 20.04.2020
 * @author YWeber */

class DrinkDayRepository @Inject constructor(private val api: GrpcConnectClient) {

    private val event = ConflatedBroadcastChannel<DrinkDayEntity>()

    fun startLoadDrinkDay() = flow {
        val drinkDay = api.getDrinkDay()
        val preview =
            PreviewDrinkDayEntity(
                drinkDay.mark.toInt(),
                drinkDay.triedBy,
                drinkDay.fortress,
                drinkDay.complication
            )
        val detailed = DetailedDrinkDayEntity(
            recipe = drinkDay.recipe,
            description = drinkDay.description,
            instruments = drinkDay.instrumentsList.map { InstrumentEntity(it.id, it.name, it.image) },
            ingredients = drinkDay.ingredientsList.map { IngredientEntity(it.id, it.name, it.volume) }
        )
        val drinkDayEntity = DrinkDayEntity(drinkDay.id, preview, detailed, drinkDay.name, drinkDay.preview)
        event.offer(drinkDayEntity)
        Timber.e(drinkDayEntity.toString())
        emit(Unit)
    }

    fun getDrinkDay() = event.asFlow()
}