package ru.yweber.flaskdionysus.ui.start

import android.os.Bundle
import android.view.View
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFlowFragment
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.di.module.MainFlowHolder
import ru.yweber.flaskdionysus.di.module.MainFlowRouter
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import toothpick.Toothpick
import toothpick.ktp.binding.module
import toothpick.ktp.delegate.inject

/**
 * Created on 30.03.2020
 * @author YWeber */

class MainFlowFragment : BaseFlowFragment(R.layout.fragment_main_flow) {

    override val viewModel: MainFlowViewModel by inject()

    override val navigator: Navigator
        get() = SupportAppNavigator(requireActivity(), childFragmentManager, R.id.containerMainFlow)

    override fun onCreate(savedInstanceState: Bundle?) {
        installViewModel<MainFlowViewModel>(parentScope, module {
            val handler = Toothpick.openRootScope().getInstance(HandleCiceroneNavigate::class.java)
            val mainFlowCicerone = handler.createCicerone(HandleCiceroneNavigate.MAIN_NAVIGATION)
            bind(Router::class.java).withName(MainFlowRouter::class.java).toInstance(mainFlowCicerone.router)
            bind(NavigatorHolder::class.java).withName(MainFlowHolder::class.java)
                .toInstance(mainFlowCicerone.navigatorHolder)
        })
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigate()
    }

    override fun backPressed(): Boolean {
        return childFragmentManager.fragments.lastOrNull {
            (it as? BaseFragment)?.backPressed() ?: false
        } is BaseFragment
    }
}