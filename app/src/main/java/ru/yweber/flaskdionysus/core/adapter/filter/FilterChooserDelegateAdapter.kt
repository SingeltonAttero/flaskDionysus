package ru.yweber.flaskdionysus.core.adapter.filter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_filter_chooser.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.adapter.state.FilterChooserItemState

/**
 * Created on 23.04.2020
 * @author YWeber */

class FilterChooserDelegateAdapter {

    fun createAdapter(choose: (id: Int) -> Unit) =
        AsyncListDifferDelegationAdapter(
            FilterChooserDiff,
            createChooseAdapter(choose)
        )

    private fun createChooseAdapter(choose: (id: Int) -> Unit) =
        adapterDelegateLayoutContainer<FilterChooserItemState, FilterChooserItemState>(R.layout.item_filter_chooser) {
            itemView.setOnClickListener {
                choose(item.id)
            }
            bind {
                tvName.text = item.name
                checkboxSelect.isChecked = item.select
            }
        }

}

private object FilterChooserDiff : DiffUtil.ItemCallback<FilterChooserItemState>() {
    override fun areItemsTheSame(oldItem: FilterChooserItemState, newItem: FilterChooserItemState): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FilterChooserItemState, newItem: FilterChooserItemState): Boolean =
        oldItem == newItem
}