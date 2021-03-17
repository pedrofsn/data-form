package br.redcode.dataform.lib.extension

/**
 * Created by pedrofsn on 31/10/2017.
 */

fun androidx.recyclerview.widget.RecyclerView.setCustomAdapter(
    adapter: androidx.recyclerview.widget.RecyclerView.Adapter<*>,
    incluirDivider: Boolean = false,
    layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager = androidx.recyclerview.widget.LinearLayoutManager(
        context
    )
) {
    setLayoutManager(layoutManager)
    setAdapter(adapter)
    setHasFixedSize(true)
    isFocusable = false

    if (incluirDivider) {
        incluirSeparador()
    }
}

fun androidx.recyclerview.widget.RecyclerView.incluirSeparador() {
    val divider = androidx.recyclerview.widget.DividerItemDecoration(
        context,
        androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
    )
    addItemDecoration(divider)
}