package ru.yweber.flaskdionysus.ui.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_error_holder.*
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.core.navigation.command.DismissDialog
import ru.yweber.flaskdionysus.core.navigation.command.ShowDialog
import ru.yweber.flaskdionysus.di.ActivityScope
import ru.yweber.flaskdionysus.di.AppScope
import ru.yweber.flaskdionysus.di.utils.ToothpickViewModelFactory
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.app.state.AppState
import toothpick.Toothpick
import toothpick.ktp.delegate.inject
import toothpick.smoothie.lifecycle.closeOnDestroy
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding


class AppActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: AppViewModel by inject<AppViewModel>()
    private val navigator
        get() = object : SupportAppNavigator(this, supportFragmentManager, R.id.containerRootActivity) {
            override fun applyCommand(command: Command) {
                when (command) {
                    is ShowDialog -> showDialog(command)
                    is DismissDialog -> dismissDialog(command)
                    else -> super.applyCommand(command)
                }
            }

            fun showDialog(command: ShowDialog) {
                val screen = command.screen
                val fragment = createFragment(screen)
                if (fragment is DialogFragment) {
                    forwardDialogFragment(fragment, screen)
                }
            }

            private fun dismissDialog(command: DismissDialog) {
                val screen = command.screen as? SupportAppScreen
                val fragment = fragmentManager.fragments.findLast { it.tag == screen?.screenKey }
                if (fragment is DialogFragment) {
                    fragment.dismiss()
                    localStackCopy.remove(screen?.screenKey)
                }
            }

            private fun forwardDialogFragment(
                fragment: DialogFragment,
                screen: SupportAppScreen
            ) {
                if (localStackCopy?.contains(screen.screenKey) == false) {
                    fragment.show(fragmentManager, screen.screenKey)
                    localStackCopy?.add(screen.screenKey)
                }
            }

            override fun setupFragmentTransaction(
                command: Command,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        initAppScope()
        super.onCreate(savedInstanceState)
        subscribe(viewModel.state, ::renderState)
        if (savedInstanceState == null) {
            viewModel.appColdStart()
        }
        btnRetry.setOnClickListener {
            viewModel.retryError()
        }
    }

    private fun initAppScope() {
        Toothpick.openScope(AppScope::class.java)
            .openSubScope(ActivityScope::class.java) {
                it.installViewModelBinding<AppViewModel>(
                    this,
                    ToothpickViewModelFactory(ActivityScope::class.java)
                ).closeOnViewModelCleared(this)
            }.closeOnDestroy(this)
            .inject(this)
    }

    private fun renderState(appState: AppState) {
        holderError.isVisible = appState.isError
        containerRootActivity.isVisible = !appState.isError
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        viewModel.addNavigator(navigator)
    }

    override fun onPause() {
        viewModel.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.lastOrNull {
            (it as? BaseFragment)?.backPressed() ?: false
        } ?: super.onBackPressed()
    }
}
