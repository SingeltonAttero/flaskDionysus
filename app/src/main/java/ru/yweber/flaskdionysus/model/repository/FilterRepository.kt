package ru.yweber.flaskdionysus.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.weber.proto.Dictionary
import ru.yweber.flaskdionysus.model.client.GrpcConnectClient
import ru.yweber.flaskdionysus.model.entity.FilterEntity
import ru.yweber.flaskdionysus.model.entity.ItemTypeFilter
import javax.inject.Inject

/**
 * Created on 22.04.2020
 * @author YWeber */

class FilterRepository @Inject constructor(private val api: GrpcConnectClient) {

    private val actionCache = ConflatedBroadcastChannel<List<FilterEntity>>(listOf())
    private val actionSelectComponent = ConflatedBroadcastChannel<Map<ItemTypeFilter, List<Int>>>(mutableMapOf())
    private val selectMapCache = mutableMapOf<ItemTypeFilter, List<Int>>()

    val selectComponentEvent
        get() = actionSelectComponent.asFlow()
            .flowOn(Dispatchers.IO)

    val cacheFilterEvent
        get() = actionCache.asFlow()
            .flowOn(Dispatchers.IO)

    suspend fun setSelectComponent(type: ItemTypeFilter, ids: List<Int>) {
        selectMapCache[type] = ids
        actionSelectComponent.send(selectMapCache)
    }


    fun startLoadFilters() = flow {
        val filters = api.getFilters()
        val ingredientsList = filters.ingredientsList
            .map { it.toFilterEntity(FilterEntity.Type.INGREDIENT) }.sortedBy { it.name }
        val otherList = filters.otherList
            .map { it.toFilterEntity(FilterEntity.Type.OTHER) }
        val volumesList = filters.volumesList
            .map { it.toFilterEntity(FilterEntity.Type.VOLUMES) }
        val fortressLevelsList = filters.fortressLevelsList
            .map { it.toFilterEntity(FilterEntity.Type.FORTRESS_LEVELS) }
        val complicationLevelsList = filters.complicationLevelsList
            .map { it.toFilterEntity(FilterEntity.Type.COMPLICATION_LEVELS) }
        actionCache.send(
            listOf(
                ingredientsList,
                otherList,
                volumesList,
                fortressLevelsList,
                complicationLevelsList
            ).flatten()
        )
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    private fun Dictionary.toFilterEntity(type: FilterEntity.Type): FilterEntity {
        return FilterEntity(id, name, type)
    }

}