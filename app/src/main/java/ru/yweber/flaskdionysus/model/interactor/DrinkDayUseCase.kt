package ru.yweber.flaskdionysus.model.interactor

import ru.yweber.flaskdionysus.model.repository.DrinkDayRepository
import javax.inject.Inject

/**
 * Created on 20.04.2020
 * @author YWeber */

class DrinkDayUseCase @Inject constructor(private val repository: DrinkDayRepository) {

    fun startDrinkDay() = repository.startLoadDrinkDay()

    fun getDrinkDay() = repository.getDrinkDay()

}