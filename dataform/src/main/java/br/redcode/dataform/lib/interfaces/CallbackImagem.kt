package br.redcode.dataform.lib.interfaces

import br.redcode.dataform.lib.ui.UIPerguntaImagem
import java.io.File

/**
 * Created by pedrofsn on 02/11/2017.
 */
interface CallbackImagem {

    var uiPerguntaImagemTemp: UIPerguntaImagem?

    fun capturarImagem(uiPerguntaImagem: UIPerguntaImagem)

    fun onImagensSelecionadas(imagesFiles: List<File>)

}