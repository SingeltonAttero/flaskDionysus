package ru.yweber.flaskdionysus.ui.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
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

    private val viewModel: AppViewModel by inject()
    private val navigator
        get() = SupportAppNavigator(this, R.id.containerRootActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        initAppScope()
        super.onCreate(savedInstanceState)
        subscribe(viewModel.state, ::renderState)
        viewModel.navigateStart()
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
        if (appState.isStartMainScreen) {
            containerRootActivity.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite))
        }
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
