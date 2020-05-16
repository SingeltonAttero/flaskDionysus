package ru.yweber.flaskdionysus.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.weber.proto.NameItem
import ru.yweber.flaskdionysus.model.client.GrpcConnectClient
import ru.yweber.flaskdionysus.model.entity.SearchEntity
import javax.inject.Inject

/**
 * Created on 15.05.2020
 * @author YWeber */

class SearchRepository @Inject constructor(private val client: GrpcConnectClient) {
    fun searchComponent(search: String, page: Int) = flow {
        val component = client.searchComponent(search, page)
        val components = component.itemsList.map {
            val type = when (it.type) {
                NameItem.Type.COCKTAIL -> SearchEntity.Type.COCKTAIL
                NameItem.Type.INGREDIENT -> SearchEntity.Type.INGREDIENT
                NameItem.Type.INSTRUMENT -> SearchEntity.Type.INSTRUMENT
                else -> error("does not type component")
            }
            SearchEntity(it.id, type, it.name)
        }
        emit(components)
    }.flowOn(Dispatchers.IO)


}