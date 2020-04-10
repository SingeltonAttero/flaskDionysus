package ru.yweber.flaskdionysus.core.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_about_drink.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.adapter.state.AboutDrinkDayItem
import ru.yweber.flaskdionysus.core.adapter.state.DrinkDayItemState
import ru.yweber.flaskdionysus.core.adapter.state.FormulaDrinkDayItem
import ru.yweber.flaskdionysus.core.adapter.state.ToolsDrinkDayItem

/**
 * Created on 10.04.2020
 * @author YWeber */

class DrinkDayDelegateAdapter {
    fun createAdapter(): AsyncListDifferDelegationAdapter<DrinkDayItemState> =
        AsyncListDifferDelegationAdapter(DrinkDayDiff, aboutDrinkAdapter())

    private fun aboutDrinkAdapter() =
        adapterDelegateLayoutContainer<AboutDrinkDayItem, DrinkDayItemState>(R.layout.item_about_drink) {
            bind {
                tvAbout.text = item.description
            }
        }

    private fun formulaAdapter() =
        adapterDelegateLayoutContainer<FormulaDrinkDayItem, DrinkDayItemState>(R.layout.item_about_drink) {
            bind {

            }
        }

    private fun toolsAdapter() =
        adapterDelegateLayoutContainer<ToolsDrinkDayItem, DrinkDayItemState>(R.layout.item_about_drink) {
            bind {

            }
        }
}

private object DrinkDayDiff : DiffUtil.ItemCallback<DrinkDayItemState>() {
    override fun areItemsTheSame(oldItem: DrinkDayItemState, newItem: DrinkDayItemState): Boolean = false

    override fun areContentsTheSame(oldItem: DrinkDayItemState, newItem: DrinkDayItemState): Boolean =
        oldItem == newItem
}