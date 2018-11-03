package br.redcode.dataform.lib.domain

import android.view.View
import br.redcode.dataform.lib.interfaces.OnItemClickListener

/**
 * Created by pedrofsn on 16/10/2017.
 */
abstract class ViewHolderGeneric<Objeto>(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    abstract fun popular(obj: Objeto)

    open fun popular(obj: Objeto, click: OnItemClickListener?) {
        if (obj != null) {
            popular(obj)

            click?.let {
                click(itemView, it)
            }
        }
    }

    fun click(view: View, clickListener: OnItemClickListener) {
        view.setOnClickListener { clickListener.onItemClickListener(adapterPosition) }
    }

    fun click(view: View, function: () -> Unit) {
        view.setOnClickListener { function.invoke() }
    }

}