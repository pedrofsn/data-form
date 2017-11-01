package br.redcode.dataform.lib.extension

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by pedrofsn on 31/10/2017.
 */

fun RecyclerView.setCustomAdapter(adapter: RecyclerView.Adapter<*>, incluirDivider: Boolean = false, layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)) {
    setLayoutManager(layoutManager)
    setAdapter(adapter)
    setHasFixedSize(true)
    isFocusable = false

    if (incluirDivider) {
        incluirSeparador()
    }
}

fun RecyclerView.incluirSeparador() {
    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    addItemDecoration(divider)
}