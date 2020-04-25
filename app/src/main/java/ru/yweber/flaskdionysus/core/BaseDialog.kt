package ru.yweber.flaskdionysus.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import ru.yweber.flaskdionysus.R
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
 * Created on 16.04.2020
 * @author YWeber */

abstract class BaseDialog(@LayoutRes private val layout: Int) : DialogFragment() {

    private lateinit var currentScope: Serializable

    private lateinit var scope: Scope

    protected open fun installModule(scope: Scope) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope = savedInstanceState?.getSerializable(SAVE_DIALOG_SCOPE_NAME) ?: objectScopeName()
        scope = if (Toothpick.isScopeOpen(currentScope)) {
            Toothpick.openScope(currentScope)
        } else {
            val openScopes = Toothpick.openScopes(ActivityScope::class.java, currentScope)
            installModule(openScopes)
            openScopes
        }.closeOnDestroy(this)
        Toothpick.inject(this, scope)
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DownloadDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layout, container, false)
    }

    /**
     * this method invoke post installModule fragment scope
     * */
    protected inline fun <reified T : ViewModel> Scope.installViewModel() {
        installViewModelBinding<T>(this@BaseDialog, ToothpickViewModelFactory(name))
        closeOnViewModelCleared(this@BaseDialog)
    }

    private companion object {
        private const val SAVE_DIALOG_SCOPE_NAME = "Save scope dialog fragment"
    }

}