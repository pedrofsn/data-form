package br.redcode.dataform.lib.domain

import br.redcode.dataform.lib.interfaces.OnCapturaImagem
import br.redcode.dataform.lib.interfaces.Permitido
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem
import java.io.File

/**
 * Created by pedrofsn on 02/11/2017.
 */
class HandlerCapturaImagem(val callback: OnCapturaImagem) : Permitido {

    var uiPerguntaImagemTemp: UIPerguntaImagem? = null

    fun capturarImagem(uiPerguntaImagem: UIPerguntaImagem, tipo: UIPerguntaImagem.Tipo) {
        uiPerguntaImagemTemp = uiPerguntaImagem
        callback.capturarImagem(tipo)
    }

    fun onImagensSelecionadas(imagesFiles: List<File>) {
        uiPerguntaImagemTemp?.let {
            imagesFiles.forEach(fun(file: File) { it.adicionarImagem(Imagem(file.absolutePath)) })
        }
    }

    fun previsualizarImagem(imagem: Imagem) {
        callback.previsualizarImagem(imagem)
    }

    override fun hasPermissoes(): Boolean {
        return callback.hasPermissoes()
    }

}