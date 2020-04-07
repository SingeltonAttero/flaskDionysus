package ru.yweber.flaskdionysus.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFlowFragment
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.di.AppScope
import ru.yweber.flaskdionysus.di.MainFlowHolder
import ru.yweber.flaskdionysus.di.MainFlowRouter
import ru.yweber.flaskdionysus.di.module.installNestedNavigation
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import toothpick.Scope
import toothpick.Toothpick
import toothpick.ktp.binding.module
import toothpick.ktp.delegate.inject

/**
 * Created on 30.03.2020
 * @author YWeber */

class MainFlowFragment : BaseFlowFragment(R.layout.fragment_main_flow) {

    override val viewModel: MainFlowViewModel by inject()

    override val navigator: Navigator
        get() = object : SupportAppNavigator(requireActivity(), childFragmentManager, R.id.containerMainFlow) {
            override fun setupFragmentTransaction(
                command: Command,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun installModule(scope: Scope) {
        scope.installNestedNavigation<MainFlowRouter, MainFlowHolder>(HandleCiceroneNavigate.MAIN_NAVIGATION)
        scope.installViewModel<MainFlowViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        if (childFragmentManager.fragments.isEmpty()) {
            viewModel.navigateToDrinks()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutItem -> {
                viewModel.navigateToAboutProject()
                true
            }
            R.id.searchItem -> {
                // TODO Search drinks
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }


    override fun backPressed(): Boolean {
        return childFragmentManager.fragments.lastOrNull {
            (it as? BaseFragment)?.backPressed() ?: false
        } is BaseFragment
    }
}