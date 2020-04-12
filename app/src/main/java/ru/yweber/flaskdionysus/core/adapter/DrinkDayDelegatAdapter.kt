package ru.yweber.flaskdionysus.core.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_about_drink.*
import kotlinx.android.synthetic.main.item_list_component.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.adapter.state.AboutDrinkDayItem
import ru.yweber.flaskdionysus.core.adapter.state.DetailedComponentItemState
import ru.yweber.flaskdionysus.core.adapter.state.DrinkDayItemState
import ru.yweber.flaskdionysus.core.adapter.state.ListComponentDetailedItem

/**
 * Created on 10.04.2020
 * @author YWeber */

class DrinkDayDelegateAdapter {
    fun createAdapter(clickComponentItem: (DetailedComponentItemState) -> Unit): AsyncListDifferDelegationAdapter<DrinkDayItemState> =
        AsyncListDifferDelegationAdapter(
            DrinkDayDiff,
            aboutDrinkAdapter(),
            listComponentDetailedAdapter(clickComponentItem)
        )

    private fun aboutDrinkAdapter() =
        adapterDelegateLayoutContainer<AboutDrinkDayItem, DrinkDayItemState>(R.layout.item_about_drink) {
            bind {
                tvAbout.text = item.description
            }
        }

    private fun listComponentDetailedAdapter(clickComponentItem: (DetailedComponentItemState) -> Unit) =
        adapterDelegateLayoutContainer<ListComponentDetailedItem, DrinkDayItemState>(R.layout.item_list_component) {
            val adapter = DetailedComponentDelegateAdapter()
                .createAdapter(clickComponentItem)
            rvDetailedDrink.adapter = adapter
            bind {
                adapter.items = item.components
            }
        }
}

private object DrinkDayDiff : DiffUtil.ItemCallback<DrinkDayItemState>() {
    override fun areItemsTheSame(oldItem: DrinkDayItemState, newItem: DrinkDayItemState): Boolean = false

    override fun areContentsTheSame(oldItem: DrinkDayItemState, newItem: DrinkDayItemState): Boolean =
        oldItem == newItem
}