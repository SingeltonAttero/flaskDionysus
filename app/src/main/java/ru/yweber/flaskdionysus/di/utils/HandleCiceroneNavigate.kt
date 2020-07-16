package ru.yweber.flaskdionysus.di.utils

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import javax.inject.Singleton

/**
 * Created on 31.03.2020
 * @author YWeber */
@Singleton
class HandleCiceroneNavigate {
    private val containers: HashMap<String, Cicerone<Router>> = HashMap()
    private var rootCicerone: Cicerone<GlobalRouter>? = null

    fun createNestedCicerone(tag: String): Cicerone<Router> {
        if (!containers.containsKey(tag)) {
            containers[tag] = Cicerone.create()
        }
        return containers[tag] ?: Cicerone.create()
    }

    fun createRootCicerone(): Cicerone<GlobalRouter> {
        var root = rootCicerone
        return if (root == null) {
            root = Cicerone.create(GlobalRouter())
            rootCicerone = root
            root
        } else {
            root
        }

    }

    companion object {
        const val MAIN_NAVIGATION = "MAIN_FLOW"
        const val DRINK_DAY_NAVIGATION = "DRINK_DAY_FLOW"
        const val DRINK_DAY_NESTED_FLOW = "DRINK_DAY_NESTED_FLOW"
    }
}