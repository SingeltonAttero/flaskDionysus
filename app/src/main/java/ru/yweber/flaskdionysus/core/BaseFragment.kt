package ru.yweber.flaskdionysus.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import ru.yweber.flaskdionysus.di.ActivityScope
import ru.yweber.flaskdionysus.di.utils.ToothpickViewModelFactory
import ru.yweber.flaskdionysus.system.objectScopeName
import toothpick.Scope
import toothpick.Toothpick
import toothpick.smoothie.lifecycle.closeOnDestroy
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding
import java.io.Serializable

/**
 * Created on 30.03.2020
 * @author YWeber */

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    private val parentScope: Serializable by lazy {
        (parentFragment as? BaseFragment)?.currentScope ?: ActivityScope::class.java
    }

    private lateinit var currentScope: Serializable
        private set

    private lateinit var scope: Scope
        private set

    protected open fun installModule(scope: Scope) {}


    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope = savedInstanceState?.getSerializable(SAVE_SCOPE_NAME) ?: objectScopeName()
        scope = if (Toothpick.isScopeOpen(currentScope)) {
            Toothpick.openScope(currentScope)
        } else {
            val openScopes = Toothpick.openScopes(parentScope, currentScope)
            installModule(openScopes)
            openScopes
        }.closeOnDestroy(this)
        Toothpick.inject(this, scope)
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SAVE_SCOPE_NAME, currentScope)
    }


    /**
     * this method invoke post installModule fragment scope
     * */
    protected inline fun <reified T : ViewModel> Scope.installViewModel() {
        installViewModelBinding<T>(this@BaseFragment, ToothpickViewModelFactory(this.name))
        closeOnViewModelCleared(this@BaseFragment)
    }


    open fun backPressed() = false

    private companion object {
        private const val SAVE_SCOPE_NAME = "Save scope fragment"
    }

}