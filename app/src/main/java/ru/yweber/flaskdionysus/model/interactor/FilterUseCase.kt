package ru.yweber.flaskdionysus.model.interactor

import ru.yweber.flaskdionysus.model.repository.FilterRepository
import javax.inject.Inject

/**
 * Created on 22.04.2020
 * @author YWeber */

class FilterUseCase @Inject constructor(private val repository: FilterRepository) {
    fun getFilter() = repository.startLoadFilters()
}