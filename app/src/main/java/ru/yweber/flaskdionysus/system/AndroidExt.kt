package ru.yweber.flaskdionysus.system

import android.content.res.Resources
import android.widget.Toast
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.yweber.flaskdionysus.core.BaseFragment

/**
 * Created on 03.04.2020
 * @author YWeber */

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.dpToPx(): Float = (this * Resources.getSystem().displayMetrics.density)

fun Int.dpToPxFloat(): Float = (this * Resources.getSystem().displayMetrics.density)

fun BaseFragment.toast(message: CharSequence) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

fun <T> AsyncListDifferDelegationAdapter<T>.setData(data: List<T>) {
    items = data
}
