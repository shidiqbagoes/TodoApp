package android.paninti.todoapp.util

import android.content.Context
import android.graphics.Color
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

val String.asColor: Int get() = Color.parseColor(this)

val Context.deviceWidth: Int get() = resources.displayMetrics.widthPixels

val Context.deviceHeight: Int get() = resources.displayMetrics.heightPixels

fun Context.font(@FontRes fontRes: Int) =
    ResourcesCompat.getFont(this, fontRes)

fun Context.drawable(@DrawableRes drawableRes: Int) =
        ContextCompat.getDrawable(this, drawableRes)

fun Context.color(@ColorRes colorRes: Int) =
        ContextCompat.getColor(this, colorRes)

fun Context.colorStateList(@ColorRes colorRes: Int) =
        ContextCompat.getColorStateList(this, colorRes)

fun Context.dimen(@DimenRes dimenRes: Int) =
        resources.getDimension(dimenRes)

inline fun <reified T> Context.array(@ArrayRes arrayRes: Int): T = when(T::class) {
        IntArray::class -> resources.getIntArray(arrayRes) as T
        Array<String>::class -> resources.getStringArray(arrayRes) as T
        Array<CharSequence>::class -> resources.getTextArray(arrayRes) as T
        else -> throw IllegalStateException("Unknown Array Class")
    }
