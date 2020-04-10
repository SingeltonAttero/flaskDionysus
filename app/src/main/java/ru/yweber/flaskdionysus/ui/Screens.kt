package ru.yweber.flaskdionysus.ui

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.yweber.flaskdionysus.ui.about.AboutProjectFragment
import ru.yweber.flaskdionysus.ui.drinkday.DrinkTheDayFlowFragment
import ru.yweber.flaskdionysus.ui.drinkday.detailed.DrinkDayDetailedFragment
import ru.yweber.flaskdionysus.ui.drinkday.preview.DrinkDayPreviewFragment
import ru.yweber.flaskdionysus.ui.home.HomeListDrinkFlowFragment
import ru.yweber.flaskdionysus.ui.main.MainFlowFragment

object Screens {

    object AboutProjectScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AboutProjectFragment()
        }
    }

    object MainFlowScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MainFlowFragment()
        }
    }

    object HomeListDrinkScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return HomeListDrinkFlowFragment()
        }
    }

    object DrinkTheDayFlowScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return DrinkTheDayFlowFragment()
        }
    }

    object DrinkDayPreviewScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return DrinkDayPreviewFragment()
        }
    }

    object DrinkDayDetailedScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return DrinkDayDetailedFragment()
        }
    }

}