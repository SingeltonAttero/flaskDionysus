package ru.yweber.flaskdionysus.core.navigation

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.yweber.flaskdionysus.core.navigation.command.DismissDialog
import ru.yweber.flaskdionysus.core.navigation.command.ShowDialog

/**
 * Created on 16.04.2020
 * @author YWeber */

class GlobalRouter : Router() {

    fun show(screen: SupportAppScreen){
        executeCommands(ShowDialog(screen))
    }

    fun dismiss(screen: SupportAppScreen){
        executeCommands(DismissDialog(screen))
    }

}