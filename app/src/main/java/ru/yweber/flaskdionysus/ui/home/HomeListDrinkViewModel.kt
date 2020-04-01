package ru.yweber.flaskdionysus.ui.home

import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.di.module.MainFlowRouter
import ru.yweber.flaskdionysus.ui.home.state.ListDrinkState
import toothpick.InjectConstructor

/**
 * Created on 31.03.2020
 * @author YWeber */

@InjectConstructor
class HomeListDrinkViewModel(@MainFlowRouter private val router: Router) :
    BaseViewModel<ListDrinkState>() {

    override val defaultState: ListDrinkState
        get() = ListDrinkState()

}