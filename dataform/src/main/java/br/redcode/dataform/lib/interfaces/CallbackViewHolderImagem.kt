package br.redcode.dataform.lib.interfaces

import android.widget.ImageView
import br.redcode.dataform.lib.model.Image

/**
 * Created by pedrofsn on 07/12/17.
 */
interface CallbackViewHolderImagem {

    fun removeImage(position: Int)

    fun visualizeImage(image: Image)
    fun loadImage(image: String, imageView: ImageView)

}