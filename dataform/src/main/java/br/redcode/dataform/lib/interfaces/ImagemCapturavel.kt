package br.redcode.dataform.lib.interfaces

import android.widget.ImageView
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem

/**
 * Created by pedrofsn on 02/11/2017.
 */
interface ImagemCapturavel {

    fun capturarImagem(tipo: UIPerguntaImagem.Tipo)

    fun visualizarImagem(imagem: Imagem)

    fun carregarImagem(imagem: String, imageView: ImageView)

    fun hasPermissoes(): Boolean

}