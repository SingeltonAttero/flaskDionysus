package ru.yweber.flaskdionysus.core.adapter.page

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import coil.api.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.hannesdorfmann.adapterdelegates4.paging.PagedListDelegationAdapter
import kotlinx.android.synthetic.main.item_drink.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.adapter.state.DrinkCardItem
import timber.log.Timber

/**
 * Created on 01.04.2020
 * @author YWeber */

class DrinksPageDelegateAdapter {

    fun createPageAdapter() =
        PagedListDelegationAdapter(DiffDrink, createDrink())

    private fun createDrink() =
        adapterDelegateLayoutContainer<DrinkCardItem, DrinkCardItem>(R.layout.item_drink) {
            bind {
                tvCookingLevel.text = item.cookingLevel
                tvIngredients.text = item.ingredients
                tvTitle.text = item.nameDrink
                ivPreviewImage.load(item.iconPath) {
                    listener(onError = { date, exc ->
                        Timber.e(exc)
                    })
                }
                loveRatingIndicator.progress(item.rating)
                tvStatusBadge.isVisible = item.iba
                ivStatusHot.isVisible = item.fire
                ivStatusPuff.isVisible = item.flacky
            }
        }

}

private object DiffDrink : DiffUtil.ItemCallback<DrinkCardItem>() {
    override fun areItemsTheSame(oldItem: DrinkCardItem, newItem: DrinkCardItem): Boolean =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DrinkCardItem, newItem: DrinkCardItem): Boolean =
        oldItem == newItem
}