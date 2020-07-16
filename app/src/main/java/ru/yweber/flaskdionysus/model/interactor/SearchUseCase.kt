package ru.yweber.flaskdionysus.model.interactor

import kotlinx.coroutines.flow.map
import ru.yweber.flaskdionysus.model.entity.SearchEntity
import ru.yweber.flaskdionysus.model.repository.SearchRepository
import javax.inject.Inject

/**
 * Created on 15.05.2020
 * @author YWeber */

class SearchUseCase @Inject constructor(private val repository: SearchRepository) {

    fun searchComponent(search: String, page: Int) = repository.searchComponent(search, page)
        .map { list ->
            list.filter { it.type == SearchEntity.Type.COCKTAIL }
        }

}