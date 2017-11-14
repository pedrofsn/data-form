package br.redcode.dataform.lib.extension

import android.view.View
import android.widget.TextView

/**
 * Created by pedrofsn on 31/10/2017.
 */

fun TextView.setTextOrHide(string: String) {
    text = string.trim()
    visibility = if (string.trim().isNotEmpty()) View.VISIBLE else View.GONE
}