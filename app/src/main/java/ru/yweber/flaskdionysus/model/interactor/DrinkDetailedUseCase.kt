package ru.yweber.flaskdionysus.model.interactor

import ru.yweber.flaskdionysus.model.repository.DrinkDetailedRepository
import javax.inject.Inject

/**
 * Created on 06.05.2020
 * @author YWeber */

class DrinkDetailedUseCase @Inject constructor(private val repository: DrinkDetailedRepository) {
    fun getDrinkDetailed(drinkId: Int) = repository.getDetailed(drinkId)
}