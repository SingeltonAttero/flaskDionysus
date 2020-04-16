package ru.yweber.flaskdionysus.di.module

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import toothpick.Scope
import toothpick.ktp.binding.module

/**
 * Created on 31.03.2020
 * @author YWeber */

fun navigationModule() = module {
    val handlerCicerone = HandleCiceroneNavigate()
    val globalCicerone = handlerCicerone.createRootCicerone()
    bind(GlobalRouter::class.java).toInstance(globalCicerone.router)
    bind(NavigatorHolder::class.java).toInstance(globalCicerone.navigatorHolder)
    bind(HandleCiceroneNavigate::class.java).toInstance(handlerCicerone)
}

inline fun <reified R : Annotation, reified H : Annotation> Scope.installNestedNavigation(constantNavigation: String) {
    installModules(module {
        val handler = getInstance(HandleCiceroneNavigate::class.java)
        val nestedCicerone = handler.createNestedCicerone(constantNavigation)
        bind(Router::class.java).withName(R::class.java).toInstance(nestedCicerone.router)
        bind(NavigatorHolder::class.java).withName(H::class.java)
            .toInstance(nestedCicerone.navigatorHolder)
    })
}