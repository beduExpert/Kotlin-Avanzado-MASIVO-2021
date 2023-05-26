package org.bedu.utils

import android.app.Activity
import android.view.View

fun Activity.getActionBarView(): View? {
    val window = this.window
    val v = window.decorView
    val resId = this.resources.getIdentifier("action_bar_container", "id", "android")
    return v.findViewById(resId)
}