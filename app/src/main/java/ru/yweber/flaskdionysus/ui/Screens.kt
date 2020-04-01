package ru.yweber.flaskdionysus.ui

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.yweber.flaskdionysus.ui.home.HomeListDrinkFragment
import ru.yweber.flaskdionysus.ui.start.MainFlowFragment

object Screens {

    object StartFlowScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MainFlowFragment()
        }
    }

    object HomeListDrinkScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return HomeListDrinkFragment()
        }
    }

}