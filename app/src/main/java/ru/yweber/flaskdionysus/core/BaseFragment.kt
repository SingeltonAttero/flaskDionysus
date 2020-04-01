package ru.yweber.flaskdionysus.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import ru.yweber.flaskdionysus.di.ActivityScope
import ru.yweber.flaskdionysus.di.utils.ToothpickViewModelFactory
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.module
import toothpick.smoothie.lifecycle.closeOnDestroy
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding

/**
 * Created on 30.03.2020
 * @author YWeber */

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    val parentScope: Any
        get() = (parentFragment as? BaseFragment)?.currentScope ?: ActivityScope::class.java

    val currentScope: Any
        get() = this::class.java

    inline fun <reified T : ViewModel> installViewModel(parentScopeName: Any, installModule: Module = module { }) {
        Toothpick.openScope(parentScopeName)
            .installModules(installModule)
            .openSubScope(currentScope) {
                it.installViewModelBinding<T>(
                    this,
                    ToothpickViewModelFactory(currentScope)
                )
                it.closeOnViewModelCleared(this)
            }
            .closeOnDestroy(this)
            .inject(this)
    }

    open fun backPressed() = false

}