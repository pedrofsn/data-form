package br.redcode.dataform.lib.domain

import android.view.View


/**
 * Created by pedrofsn on 16/10/2017.
 */
abstract class ViewHolderGeneric<Objeto>(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    abstract fun popular(obj: Objeto)

    open fun popular(obj: Objeto, click: ((Int) -> Unit)?) {
        if (obj != null) {
            popular(obj)

            click?.let {
                click(itemView, it)
            }
        }
    }

    fun click(view: View, clickListener: ((Int) -> Unit)) {
        view.setOnClickListener { clickListener.invoke(adapterPosition) }
    }

    fun click(view: View, function: () -> Unit) {
        view.setOnClickListener { function.invoke() }
    }

}