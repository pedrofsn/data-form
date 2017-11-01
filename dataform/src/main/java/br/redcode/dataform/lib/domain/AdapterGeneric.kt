package br.redcode.dataform.lib.domain

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import java.util.*

/**
 * Created by pedrofsn on 16/10/2017.
 */
abstract class AdapterGeneric<Objeto, VH : ViewHolderGeneric<Objeto>> : RecyclerView.Adapter<VH>() {

    abstract var myOnItemClickListener: OnItemClickListener?
    private val lista = ArrayList<Objeto>()

    abstract val layout: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layout, parent, false)
        return getViewHolder(view)
    }

    abstract fun getViewHolder(view: View): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.popular(getLista()[position], myOnItemClickListener)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun getLista(): List<Objeto> {
        return lista
    }

    fun setLista(lista: List<Objeto>?) {
        if (lista != null) {
            this.lista.clear()
            this.lista.addAll(lista)
            notifyDataSetChanged()
        }
    }

    fun addAll(novaLista: List<Objeto>?) {
        if (novaLista != null) {
            val tamanhoAtual = this.lista.size
            val tamanhoNovo = novaLista.size
            val total = tamanhoAtual + tamanhoNovo

            this.lista.addAll(novaLista)
            notifyItemRangeInserted(tamanhoAtual, total)
        }
    }
}