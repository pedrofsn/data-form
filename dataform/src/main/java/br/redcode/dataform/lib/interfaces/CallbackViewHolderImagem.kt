package br.redcode.dataform.lib.interfaces

import android.widget.ImageView
import br.redcode.dataform.lib.model.Imagem

/**
 * Created by pedrofsn on 07/12/17.
 */
interface CallbackViewHolderImagem {

    fun removerImagem(posicao: Int)
    fun visualizarImagem(imagem: Imagem)
    fun carregarImagem(imagem: String, imageView: ImageView)

}