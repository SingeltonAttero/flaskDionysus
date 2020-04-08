package ru.yweber.flaskdionysus.ui.drinkday.detailed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_drink_day_detailed.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.ui.Screens
import toothpick.Scope
import toothpick.ktp.delegate.inject
import java.util.*

/**
 * Created on 07.04.2020
 * @author YWeber */

class DrinkDayDetailedFragment : BaseFragment(R.layout.fragment_drink_day_detailed) {

    private val viewModel by inject<DrinkDayDetailedViewModel>()
    private lateinit var layoutMediator: TabLayoutMediator


    private var fragmentAdapter: DetailedTabAdapter? = null

    override fun installModule(scope: Scope) {
        scope.installViewModel<DrinkDayDetailedViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutMediator = TabLayoutMediator(tabLayoutDetailedDrinkDay, viewPagerDetailedDrinkDay) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.aboutDrink).toUpperCase(Locale.getDefault())
                1 -> tab.text = getString(R.string.formula).toUpperCase(Locale.getDefault())
                2 -> tab.text = getString(R.string.tools).toUpperCase(Locale.getDefault())
            }
        }
        fragmentAdapter = DetailedTabAdapter(this)
        viewPagerDetailedDrinkDay.adapter = fragmentAdapter
        layoutMediator.attach()
    }

    override fun onDestroyView() {
        viewPagerDetailedDrinkDay.adapter = null
        tabLayoutDetailedDrinkDay.removeAllTabs()
        layoutMediator.detach()
        super.onDestroyView()
    }

    private inner class DetailedTabAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> Screens.AboutDrinkScreen.fragment
            1 -> Screens.FormulaDrinkScreen.fragment
            else -> Screens.ToolsDrinkScreen.fragment
        }
    }
}