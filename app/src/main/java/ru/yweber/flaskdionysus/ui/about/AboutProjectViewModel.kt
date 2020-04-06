package ru.yweber.flaskdionysus.ui.about

import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.ui.about.state.AboutState
import toothpick.InjectConstructor

/**
 * Created on 06.04.2020
 * @author YWeber */
@InjectConstructor
class AboutProjectViewModel(private val router: Router) : BaseViewModel<AboutState>() {
    fun backTo() {
        router.backTo(null)
    }

    override val defaultState: AboutState
        get() = AboutState()

}