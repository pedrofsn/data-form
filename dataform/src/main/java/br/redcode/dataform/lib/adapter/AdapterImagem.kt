package br.redcode.dataform.lib.adapter

import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderImagem
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderImagemComLegenda
import br.redcode.dataform.lib.domain.AdapterGeneric
import br.redcode.dataform.lib.interfaces.CallbackViewHolderImagem
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Imagem

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterImagem(val callback: CallbackViewHolderImagem, val configuracao: ConfiguracaoFormulario, val comLegenda: Boolean = false) : AdapterGeneric<Imagem, ViewHolderImagem>() {

    override var myOnItemClickListener: OnItemClickListener? = null

    override val layout: Int = if (comLegenda) R.layout.adapter_imagem_com_legenda else R.layout.adapter_imagem

    override fun getViewHolder(view: View): ViewHolderImagem {
        return if (comLegenda) ViewHolderImagemComLegenda(view) else ViewHolderImagem(view)
    }

    override fun onBindViewHolder(holder: ViewHolderImagem, position: Int) {
        holder.popular(getLista()[position], callback, configuracao)
    }

}