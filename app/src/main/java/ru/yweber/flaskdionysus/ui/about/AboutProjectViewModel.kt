package ru.yweber.flaskdionysus.ui.about

import io.grpc.ManagedChannelBuilder
import ru.terrakok.cicerone.Router
import ru.weber.proto.DrinksGrpc
import ru.weber.proto.DrinksRequest
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.ui.about.state.AboutState
import toothpick.InjectConstructor

/**
 * Created on 06.04.2020
 * @author YWeber */
@InjectConstructor
class AboutProjectViewModel(private val router: Router) : BaseViewModel<AboutState>() {

    override val defaultState: AboutState
        get() = AboutState("")

    fun backTo() {
        router.backTo(null)
    }

}