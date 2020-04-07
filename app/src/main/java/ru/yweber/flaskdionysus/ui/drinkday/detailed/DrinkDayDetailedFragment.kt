package ru.yweber.flaskdionysus.ui.drinkday.detailed

import android.os.Bundle
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
    private val fragmentAdapter by lazy { DetailedTabAdapter(this) }

    override fun installModule(scope: Scope) {
        scope.installViewModel<DrinkDayDetailedViewModel>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        layoutMediator = TabLayoutMediator(tabLayoutDetailedDrinkDay, viewPagerDetailedDrinkDay) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.aboutDrink).toUpperCase(Locale.getDefault())
                1 -> tab.text = getString(R.string.formula).toUpperCase(Locale.getDefault())
                2 -> tab.text = getString(R.string.tools).toUpperCase(Locale.getDefault())
            }
        }
        viewPagerDetailedDrinkDay.adapter = fragmentAdapter
        layoutMediator.attach()
    }

    override fun onDestroyView() {
        viewPagerDetailedDrinkDay.adapter = null
        layoutMediator.detach()
        super.onDestroyView()
    }

    private class DetailedTabAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {
        private val detailedFragment = listOf(
            Screens.AboutDrinkScreen.fragment,
            Screens.FormulaDrinkScreen.fragment,
            Screens.ToolsDrinkScreen.fragment
        )

        override fun getItemCount(): Int = detailedFragment.size

        override fun createFragment(position: Int): Fragment = detailedFragment[position]

    }

}