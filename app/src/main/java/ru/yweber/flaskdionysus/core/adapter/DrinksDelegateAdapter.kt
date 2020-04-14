package ru.yweber.flaskdionysus.core.adapter

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import coil.api.load
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_drink.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.adapter.state.DrinkCardItem
import ru.yweber.flaskdionysus.core.adapter.state.DrinkItem

/**
 * Created on 01.04.2020
 * @author YWeber */

class DrinksDelegateAdapter {

    fun createAdapter() =
        AsyncListDifferDelegationAdapter(DiffDrink, createDrink())

    private fun createDrink() =
        adapterDelegateLayoutContainer<DrinkCardItem, DrinkItem>(R.layout.item_drink) {

            bind {
                tvCookingLevel.text = item.cookingLevel
                tvIngredients.text = item.ingredients
                tvTitle.text = item.nameDrink
                ivPreviewImage.load(item.iconPath)
                loveRatingIndicator.progress(item.rating)
                tvStatusBadge.isVisible = item.iba
                ivStatusHot.isVisible = item.fire
                ivStatusPuff.isVisible = item.flacky
            }
        }

}

private object DiffDrink : DiffUtil.ItemCallback<DrinkItem>() {
    override fun areItemsTheSame(oldItem: DrinkItem, newItem: DrinkItem): Boolean = false

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DrinkItem, newItem: DrinkItem): Boolean =
        oldItem == newItem
}