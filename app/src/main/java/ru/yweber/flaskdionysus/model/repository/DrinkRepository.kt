package ru.yweber.flaskdionysus.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.weber.proto.DrinksRequest
import ru.yweber.flaskdionysus.BuildConfig
import ru.yweber.flaskdionysus.model.client.GrpcConnectClient
import ru.yweber.flaskdionysus.model.entity.DrinkEntity
import ru.yweber.flaskdionysus.model.entity.DrinksEntity
import ru.yweber.flaskdionysus.system.error.ErrorStatusSender
import timber.log.Timber
import javax.inject.Inject

/**
 * Created on 14.04.2020
 * @author YWeber */

class DrinkRepository @Inject constructor(
    private val client: GrpcConnectClient,
    private val errorStatus: ErrorStatusSender
) {
    fun pageDrink(
        page: Int,
        includes: List<Int>,
        except: List<Int>,
        fortress: List<Int>,
        volumes: List<Int>,
        another: List<Boolean>
    ) = flow<DrinksEntity> {
        val drinkRequest = DrinksRequest.newBuilder()
            .setPage(page)
            .addAllIncludes(includes)
            .addAllExcept(except)
            .addAllFortress(fortress)
            .addAllVolume(volumes)
            .setIsFlacky(another.getOrNull(0) ?: false)
            .setIsFire(another.getOrNull(1) ?: false)
            .setIsIba(another.getOrNull(2) ?: false)
            .build()
        val pageDrinks = try {
            convertGrpcToEntity(drinkRequest)
        } catch (ex: Throwable) {
            Timber.e(ex)
            throw ex
        }
        emit(DrinksEntity.Result(pageDrinks))
    }.catch {
        emit(DrinksEntity.Error(errorStatus.sender(it)))
    }.flowOn(Dispatchers.IO)

    private suspend fun convertGrpcToEntity(drinkRequest: DrinksRequest): List<DrinkEntity> {
        return client.getDrinkList(drinkRequest).drinksList
            .map {
                DrinkEntity(
                    it.id, it.name,
                    "http://${BuildConfig.ENDPOINT}:9001${it.icon}", it.mark,
                    it.isFlacky, it.isFire,
                    it.isIba, it.properties, it.ingredients
                )
            }
    }
}