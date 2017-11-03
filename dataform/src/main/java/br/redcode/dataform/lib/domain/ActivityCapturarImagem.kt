package br.redcode.dataform.lib.domain

import android.Manifest
import android.support.v7.app.AppCompatActivity
import br.redcode.dataform.lib.interfaces.OnCapturaImagem
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem


/**
 * Created by pedrofsn on 02/11/2017.
 */
abstract class ActivityCapturarImagem : AppCompatActivity() {

    val permissoes: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val handlerCapturaImagem = HandlerCapturaImagem(object : OnCapturaImagem {

        override fun hasPermissoes(): Boolean {
            return hasTodasPermissoesAtivas()
        }

        override fun previsualizarImagem(imagem: Imagem) {
            previsualizarImagem(imagem.imagem)
        }

        override fun capturarImagem(tipo: UIPerguntaImagem.Tipo) {
            capturarImagemComContext(tipo)
        }
    })

    abstract fun capturarImagemComContext(tipo: UIPerguntaImagem.Tipo)

    abstract fun hasTodasPermissoesAtivas(): Boolean

    abstract fun previsualizarImagem(caminho: String)

}