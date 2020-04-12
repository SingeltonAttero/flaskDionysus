package ru.yweber.flaskdionysus.core.adapter

import androidx.recyclerview.widget.DiffUtil
import coil.api.load
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_description_component.*
import kotlinx.android.synthetic.main.item_formula_component.*
import kotlinx.android.synthetic.main.item_tool_component.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.adapter.state.DescriptionComponentItem
import ru.yweber.flaskdionysus.core.adapter.state.DetailedComponentItemState
import ru.yweber.flaskdionysus.core.adapter.state.FormulaComponentItem
import ru.yweber.flaskdionysus.core.adapter.state.ToolComponentItem

/**
 * Created on 11.04.2020
 * @author YWeber */

class DetailedComponentDelegateAdapter {

    fun createAdapter(clickItem: (DetailedComponentItemState) -> Unit) =
        AsyncListDifferDelegationAdapter(
            DiffDetailedComponent,
            formulaAdapter(clickItem),
            toolsAdapter(clickItem),
            descriptionAdapter()
        )

    private fun formulaAdapter(clickItem: (DetailedComponentItemState) -> Unit) =
        adapterDelegateLayoutContainer<FormulaComponentItem, DetailedComponentItemState>(R.layout.item_formula_component) {
            itemView.setOnClickListener { clickItem(item) }
            bind {
                tvIngredientName.text = item.ingredientsName
                tvValue.text = item.volume
            }
        }

    private fun toolsAdapter(clickItem: (DetailedComponentItemState) -> Unit) =
        adapterDelegateLayoutContainer<ToolComponentItem, DetailedComponentItemState>(R.layout.item_tool_component) {
            itemView.setOnClickListener { clickItem(item) }
            bind {
                tvToolName.text = item.toolName
                ivIconTool.load(item.toolIcon)
            }
        }

    private fun descriptionAdapter() =
        adapterDelegateLayoutContainer<DescriptionComponentItem, DetailedComponentItemState>(R.layout.item_description_component) {
            bind {
                tvDescription.text = item.description
            }
        }


}

private object DiffDetailedComponent : DiffUtil.ItemCallback<DetailedComponentItemState>() {
    override fun areItemsTheSame(oldItem: DetailedComponentItemState, newItem: DetailedComponentItemState): Boolean =
        false

    override fun areContentsTheSame(oldItem: DetailedComponentItemState, newItem: DetailedComponentItemState): Boolean =
        oldItem == newItem
}