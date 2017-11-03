package br.redcode.dataform.lib.interfaces

import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem

/**
 * Created by pedrofsn on 02/11/2017.
 */
interface OnCapturaImagem : Permitido, ImagemCarregavel {

    fun capturarImagem(tipo: UIPerguntaImagem.Tipo)

    fun previsualizarImagem(imagem: Imagem)

}