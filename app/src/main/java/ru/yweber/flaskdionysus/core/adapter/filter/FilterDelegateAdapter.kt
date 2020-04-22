package ru.yweber.flaskdionysus.core.adapter.filter

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.transition.TransitionManager
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateLayoutContainerViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_filter.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.adapter.state.FilterItemState

/**
 * Created on 22.04.2020
 * @author YWeber */

class FilterDelegateAdapter {
    fun createAdapter(closeChip: (name: String, position: Int) -> Unit, openPartFilter: (nameFilter: String) -> Unit) =
        AsyncListDifferDelegationAdapter(
            FilterDiff,
            filterAdapter(closeChip, openPartFilter)
        )

    private fun filterAdapter(
        closeChip: (name: String, position: Int) -> Unit,
        openPartFilter: (nameFilter: String) -> Unit
    ) =
        adapterDelegateLayoutContainer<FilterItemState, FilterItemState>(R.layout.item_filter) {
            val inflater = LayoutInflater.from(context)
            val closeListener = View.OnClickListener {
                if (it is Chip) {
                    closeChip.invoke(it.text.toString(), adapterPosition)
                }
            }
            tvAddChips.setOnClickListener { openPartFilter.invoke(item.title) }
            bind {
                TransitionManager.beginDelayedTransition(chipGroupFilter)
                chipGroupFilter.removeAllViews()
                inflateChip(inflater).forEach {
                    chipGroupFilter.addView(it)
                    it.setOnCloseIconClickListener(closeListener)
                }
                tvTitleFilter.text = item.title
                tvAddChips.text = item.nameFilterButton
            }
        }

    private fun AdapterDelegateLayoutContainerViewHolder<FilterItemState>.inflateChip(
        inflater: LayoutInflater
    ): List<Chip> {
        return item.chips
            .map {
                val chip = inflater.inflate(R.layout.view_chip_filter, null, false) as Chip
                chip.text = it.title
                chip.id = it.id + adapterPosition
                chip
            }
    }
}

private object FilterDiff : DiffUtil.ItemCallback<FilterItemState>() {
    override fun areItemsTheSame(oldItem: FilterItemState, newItem: FilterItemState): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: FilterItemState, newItem: FilterItemState): Boolean =
        oldItem == newItem
}