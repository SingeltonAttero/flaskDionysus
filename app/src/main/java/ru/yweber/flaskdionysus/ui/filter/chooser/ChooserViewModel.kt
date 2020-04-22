package ru.yweber.flaskdionysus.ui.filter.chooser

import kotlinx.coroutines.flow.collect
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.model.interactor.FilterUseCase
import ru.yweber.flaskdionysus.ui.filter.chooser.state.ChooserState
import toothpick.InjectConstructor

/**
 * Created on 22.04.2020
 * @author YWeber */

@InjectConstructor
class ChooserViewModel(private val useCase: FilterUseCase) : BaseViewModel<ChooserState>() {
    override val defaultState: ChooserState
        get() = ChooserState("")

    init {
        launch {
            useCase.getFilter()
                .collect {
                    action.value = currentState.copy(test = "$it \n")
                }
        }
    }
}