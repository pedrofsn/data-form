package br.redcode.dataform.lib.interfaces

import br.redcode.dataform.lib.ui.UIPerguntaImagem

/**
 * Created by pedrofsn on 02/11/2017.
 */
interface OnCapturaImagem {

    fun capturarImagem(tipo: UIPerguntaImagem.Tipo)

}