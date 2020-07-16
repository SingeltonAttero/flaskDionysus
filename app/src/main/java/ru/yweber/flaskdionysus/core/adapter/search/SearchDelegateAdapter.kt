package ru.yweber.flaskdionysus.core.adapter.search

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.hannesdorfmann.adapterdelegates4.paging.PagedListDelegationAdapter
import kotlinx.android.synthetic.main.item_search_drink.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.adapter.state.DrinkSearchItemState

/**
 * Created on 15.05.2020
 * @author YWeber */

class SearchDelegateAdapter {

    fun createAdapter(click: (DrinkSearchItemState) -> Unit) =
        PagedListDelegationAdapter(SearchDiff, adapterDrink(click))

    private fun adapterDrink(click: (DrinkSearchItemState) -> Unit) =
        adapterDelegateLayoutContainer<DrinkSearchItemState, DrinkSearchItemState>(R.layout.item_search_drink) {
            itemView.setOnClickListener {
                click.invoke(item)
            }
            bind {
                tvType.text = item.typeDescription
                tvName.text = item.name
            }
        }

}

private object SearchDiff : DiffUtil.ItemCallback<DrinkSearchItemState>() {
    override fun areItemsTheSame(oldItem: DrinkSearchItemState, newItem: DrinkSearchItemState): Boolean =
        oldItem.type == newItem.type && oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: DrinkSearchItemState, newItem: DrinkSearchItemState): Boolean =
        oldItem == newItem
}