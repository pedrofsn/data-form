package br.redcode.dataform.lib.domain

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import br.redcode.dataform.lib.domain.handlers.HandlerCapturaImagem
import br.redcode.dataform.lib.interfaces.ImagemCapturavel
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem

/**
 * Created by pedrofsn on 02/11/2017.
 */
abstract class ActivityCapturarImagem : AppCompatActivity(), ImagemCapturavel {

    val permissoes: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val handlerCapturaImagem = HandlerCapturaImagem(this)

    abstract override fun capturarImagem(tipo: UIPerguntaImagem.Tipo)

    abstract override fun hasPermissoes(): Boolean

    abstract override fun visualizarImagem(imagem: Imagem)

    abstract override fun carregarImagem(imagem: String, imageView: ImageView)

}