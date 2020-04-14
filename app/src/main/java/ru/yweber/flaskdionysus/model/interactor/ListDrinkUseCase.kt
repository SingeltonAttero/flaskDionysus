package ru.yweber.flaskdionysus.model.interactor

import ru.yweber.flaskdionysus.model.repository.DrinkRepository
import javax.inject.Inject

/**
 * Created on 14.04.2020
 * @author YWeber */

class ListDrinkUseCase @Inject constructor(private val repository: DrinkRepository) {
    fun pageDrinks(page: Int) = repository.pageDrink(page)
}