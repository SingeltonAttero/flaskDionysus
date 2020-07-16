package ru.yweber.flaskdionysus.core.adapter

import android.util.TypedValue
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_about_drink.*
import kotlinx.android.synthetic.main.item_component_main.*
import kotlinx.android.synthetic.main.item_list_component.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.adapter.state.*
import ru.yweber.flaskdionysus.system.dpToPx

/**
 * Created on 10.04.2020
 * @author YWeber */

class DrinkDayDelegateAdapter(private val isCard: Boolean = false) {
    fun createAdapter(clickComponentItem: (DetailedComponentItemState) -> Unit): AsyncListDifferDelegationAdapter<DrinkDayItemState> =
        AsyncListDifferDelegationAdapter(
            DrinkDayDiff,
            aboutDrinkAdapter(),
            listComponentDetailedAdapter(clickComponentItem),
            mainAdapter()
        )

    private fun aboutDrinkAdapter() =
        adapterDelegateLayoutContainer<AboutDrinkComponentItem, DrinkDayItemState>(R.layout.item_about_drink) {
            initCard(cardAboutDrink)
            if (isCard) {
                tvAbout.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            }
            bind {
                tvAbout.text = item.description
            }
        }

    private fun listComponentDetailedAdapter(clickComponentItem: (DetailedComponentItemState) -> Unit) =
        adapterDelegateLayoutContainer<ListComponentDetailedItem, DrinkDayItemState>(R.layout.item_list_component) {
            val adapter = DetailedComponentDelegateAdapter()
                .createAdapter(clickComponentItem)
            rvDetailedDrink.adapter = adapter
            initCard(cardViewListComponent)
            bind {
                adapter.items = item.components
            }
        }

    private fun mainAdapter() =
        adapterDelegateLayoutContainer<MainComponentDetailedItem, DrinkDayItemState>(R.layout.item_component_main) {
            bind {
                tvTriedDrink.text = item.tried
                tvCookingLevel.text = item.complication
                tvAlcoholStrength.text = item.fortress
                tvStatusBadge.isVisible = item.isIba
                ivStatusHot.isVisible = item.isFire
                ivStatusPuff.isVisible = item.isPuff
                tvDescriptionHot.isVisible = item.isFire
                tvDescriptionIba.isVisible = item.isIba
                tvDescriptionPuff.isVisible = item.isPuff
            }
        }

    private fun initCard(viewGroup: ViewGroup) {
        if (isCard) {
            val layoutParams = viewGroup.layoutParams as FrameLayout.LayoutParams
            layoutParams.marginStart = 4.dpToPx()
            layoutParams.marginEnd = 4.dpToPx()
            layoutParams.topMargin = 4.dpToPx()
            layoutParams.bottomMargin = 16.dpToPx()
            layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
            viewGroup.layoutParams = layoutParams
            viewGroup.setPadding(0, 0, 0, 16)
        }
    }
}

private object DrinkDayDiff : DiffUtil.ItemCallback<DrinkDayItemState>() {
    override fun areItemsTheSame(oldItem: DrinkDayItemState, newItem: DrinkDayItemState): Boolean = false

    override fun areContentsTheSame(oldItem: DrinkDayItemState, newItem: DrinkDayItemState): Boolean =
        oldItem == newItem
}