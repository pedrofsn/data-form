package br.redcode.dataform.lib.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by pedrofsn on 16/10/2017.
 */
abstract class AdapterGeneric<Objeto, VH : ViewHolderGeneric<Objeto>> : androidx.recyclerview.widget.RecyclerView.Adapter<VH>() {

    abstract var myOnItemClickListener: ((Int) -> Unit)?
    private val lista = ArrayList<Objeto>()

    abstract val layout: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layout, parent, false)
        return getViewHolder(view)
    }

    abstract fun getViewHolder(view: View): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.popular(getList()[position], myOnItemClickListener)
    }

    override fun getItemCount(): Int = lista.size

    fun getList(): ArrayList<Objeto> = lista

    fun adicionar(objeto: Objeto) {
        this.lista.add(objeto)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        this.lista.removeAt(position)
        notifyDataSetChanged()
    }

    fun setLista(lista: ArrayList<Objeto>?) {
        lista?.let {
            this.lista.clear()
            this.lista.addAll(lista)
            notifyDataSetChanged()
        }
    }

    fun setLista(lista: List<Objeto>?) {
        lista?.let {
            this.lista.clear()
            this.lista.addAll(lista)
            notifyDataSetChanged()
        }
    }

    fun addAll(novaLista: ArrayList<Objeto>?) {
        novaLista?.let {
            val tamanhoAtual = this.lista.size
            val tamanhoNovo = novaLista.size
            val total = tamanhoAtual + tamanhoNovo

            this.lista.addAll(novaLista)
            notifyItemRangeInserted(tamanhoAtual, total)
        }
    }

    fun addAll(novaLista: List<Objeto>?) {
        novaLista?.let {
            val tamanhoAtual = this.lista.size
            val tamanhoNovo = novaLista.size
            val total = tamanhoAtual + tamanhoNovo

            this.lista.addAll(novaLista)
            notifyItemRangeInserted(tamanhoAtual, total)
        }
    }
}