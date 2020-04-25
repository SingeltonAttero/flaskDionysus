package ru.yweber.flaskdionysus.model.interactor

import ru.yweber.flaskdionysus.model.repository.DrinkRepository
import ru.yweber.flaskdionysus.model.repository.FilterRepository
import javax.inject.Inject

/**
 * Created on 14.04.2020
 * @author YWeber */

class ListDrinkUseCase @Inject constructor(
    private val repository: DrinkRepository,
    private val filterRepository: FilterRepository
) {
    fun pageDrinks(page: Int) = repository.pageDrink(page)
}