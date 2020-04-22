package ru.yweber.flaskdionysus.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.weber.proto.Dictionary
import ru.yweber.flaskdionysus.model.client.GrpcConnectClient
import ru.yweber.flaskdionysus.model.entity.FilterEntity
import javax.inject.Inject

/**
 * Created on 22.04.2020
 * @author YWeber */

class FilterRepository @Inject constructor(private val api: GrpcConnectClient) {


    fun startLoadFilters() = flow {
        val filters = api.getFilters()
        val ingredientsList = filters.ingredientsList
            .map { it.toFilterEntity(FilterEntity.Type.INGREDIENT) }
        val otherList = filters.otherList
            .map { it.toFilterEntity(FilterEntity.Type.OTHER) }
        val volumesList = filters.volumesList
            .map { it.toFilterEntity(FilterEntity.Type.VOLUMES) }
        val fortressLevelsList = filters.fortressLevelsList
            .map { it.toFilterEntity(FilterEntity.Type.FORTRESS_LEVELS) }
        val complicationLevelsList = filters.complicationLevelsList
            .map { it.toFilterEntity(FilterEntity.Type.COMPLICATION_LEVELS) }
        emit(listOf(ingredientsList, otherList, volumesList, fortressLevelsList, complicationLevelsList).flatten())
    }.flowOn(Dispatchers.IO)

    private fun Dictionary.toFilterEntity(type: FilterEntity.Type): FilterEntity {
        return FilterEntity(id, name, type)
    }

}