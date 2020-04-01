package ru.yweber.flaskdionysus.di.module

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import toothpick.ktp.binding.module

/**
 * Created on 31.03.2020
 * @author YWeber */

fun navigationModule() = module {
    val handlerCicerone = HandleCiceroneNavigate()
    val globalCicerone = handlerCicerone.createCicerone(HandleCiceroneNavigate.APP_NAVIGATION)
    bind(Router::class.java).toInstance(globalCicerone.router)
    bind(NavigatorHolder::class.java).toInstance(globalCicerone.navigatorHolder)
    bind(HandleCiceroneNavigate::class.java).toInstance(handlerCicerone)
}