package ru.yweber.flaskdionysus.di.utils

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * Created on 31.03.2020
 * @author YWeber */
@Singleton
class HandleCiceroneNavigate {
    private val containers: HashMap<String, Cicerone<Router>> = HashMap()

    fun createCicerone(tag: String): Cicerone<Router> {
        if (!containers.containsKey(tag)) {
            containers[tag] = Cicerone.create()
        }
        return containers[tag] ?: Cicerone.create()
    }

    companion object {
        const val APP_NAVIGATION = "APP_GLOBAL"
        const val MAIN_NAVIGATION = "MAIN_FLOW"
        const val DRINK_DAY_NAVIGATION = "DRINK_DAY_FLOW"
        const val DRINK_DAY_NESTED_FLOW = "DRINK_DAY_NESTED_FLOW"
    }
}