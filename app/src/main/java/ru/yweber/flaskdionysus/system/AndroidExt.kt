package ru.yweber.flaskdionysus.system

import android.content.res.Resources
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

/**
 * Created on 03.04.2020
 * @author YWeber */

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.dpToPx(): Float = (this * Resources.getSystem().displayMetrics.density)

fun Int.dpToPxFloat(): Float = (this * Resources.getSystem().displayMetrics.density)

fun Fragment.toast(message: CharSequence) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

fun ImageView.setVector(@DrawableRes drawableRes: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
}

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun <T> AsyncListDifferDelegationAdapter<T>.setData(data: List<T>) {
    items = data
}
