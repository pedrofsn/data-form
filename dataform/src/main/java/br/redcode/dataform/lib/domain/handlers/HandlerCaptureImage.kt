package br.redcode.dataform.lib.domain.handlers

import android.widget.ImageView
import br.redcode.dataform.lib.interfaces.ImageCapturable
import br.redcode.dataform.lib.model.Image
import br.redcode.dataform.lib.ui.UIQuestionImage

/**
 * Created by pedrofsn on 02/11/2017.
 */
class HandlerCaptureImage(val callback: ImageCapturable) {

    var uiQuestionImageTemp: UIQuestionImage? = null

    fun capturarImagem(uiQuestionImage: UIQuestionImage, type: UIQuestionImage.Type) {
        uiQuestionImageTemp = uiQuestionImage
        callback.captureImage(type)
    }

    fun onImagensSelecionadas(vararg imagens: Image) {
        imagens.forEach { uiQuestionImageTemp?.addImage(it) }
    }

    fun visualizarImagem(image: Image) {
        callback.visualizeImage(image)
    }

    fun carregarImagem(imagem: String, imageView: ImageView) {
        callback.loadImage(imagem, imageView)
    }

    fun hasPermissoes(): Boolean {
        return callback.hasPermissions()
    }

}