package ru.yweber.flaskdionysus.ui.drinkday.preview

import android.os.Bundle
import coil.api.load
import kotlinx.android.synthetic.main.fragment_drink_day_preview.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 07.04.2020
 * @author YWeber */

class DrinkDayPreviewFragment : BaseFragment(R.layout.fragment_drink_day_preview) {

    private val viewModel by inject<DrinkDayPreviewViewModel>()

    override fun installModule(scope: Scope) {
        scope.installViewModel<DrinkDayPreviewViewModel>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ivPreviewDrinkDay.load("https://kak-nazyvaetsya.ru/wp-content/uploads/2019/05/b52.jpg")
    }
}