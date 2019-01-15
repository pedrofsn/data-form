package br.redcode.dataform.lib.interfaces

import android.widget.ImageView
import br.redcode.dataform.lib.model.Image
import br.redcode.dataform.lib.ui.UIQuestionImage

/**
 * Created by pedrofsn on 02/11/2017.
 */
interface ImageCapturable {

    fun captureImage(type: UIQuestionImage.Type)
    fun hasPermissions(): Boolean

    fun visualizeImage(image: Image)
    fun loadImage(imagem: String, imageView: ImageView)

}