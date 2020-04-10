package ru.yweber.flaskdionysus.ui.drinkday.detailed

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.AboutDrinkDayItem
import ru.yweber.flaskdionysus.ui.drinkday.detailed.state.DrinkDayDetailedState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDayDetailedViewModel : BaseViewModel<DrinkDayDetailedState>() {
    override val defaultState: DrinkDayDetailedState
        get() = DrinkDayDetailedState(listOf())

    init {
        action.value = currentState.copy(
            listOf(
                AboutDrinkDayItem("Фраппе с арахисовой пастой. Измельчите в пюре 1/4 ст. арахисовой пасты с 1 ст. молока. Налейте в стакан немного шоколадного сиропа, сверху добавьте молоко с пастой. Медленно влейте газированную воду и размешайте. Источник: https://grandkulinar.ru/7418-50-holodnyh-napitkov.html Гранд Кулинар"),
                AboutDrinkDayItem(". Смузи с медовой дыней. Пюрируйте по 1 ст. замороженной медовой дыни и замороженных кубиков огурца вместе с соком 1 лайма и сахаром. Рецепт смузи с замороженным манго «Батидос» и гуавой Источник: https://grandkulinar.ru/7418-50-holodnyh-napitkov.html Гранд Кулинар://grandkulinar.ru/7418-50-holodnyh-napitkov.html Гранд Кулинар"),
                AboutDrinkDayItem("«Спритц» с саке. В шейкере со льдом соедините 1 листик базилика, 6 листьев мяты, 2 полоски из цедры лимона, 1 дольку апельсина, 1/4 ст. нарезанного кубиками огурца и 45 мл. апельсинового ликера; встряхните. Вылейте в стакан и сверху долейте игристое саке. Источник: https://grandkulinar.ru/7418-50-holodnyh-napitkov.html Гранд Кулинар")
            )
        )
    }
}