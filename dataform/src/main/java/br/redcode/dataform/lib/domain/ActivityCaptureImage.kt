package br.redcode.dataform.lib.domain

import android.Manifest
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import br.redcode.dataform.lib.domain.handlers.HandlerCaptureImage
import br.redcode.dataform.lib.interfaces.ImageCapturable
import br.redcode.dataform.lib.model.Image
import br.redcode.dataform.lib.ui.UIQuestionImage

/**
 * Created by pedrofsn on 02/11/2017.
 */
abstract class ActivityCaptureImage : AppCompatActivity(), ImageCapturable {

    val permissoes: Array<String> = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val handlerCapturaImagem = HandlerCaptureImage(this)

    abstract override fun captureImage(type: UIQuestionImage.Type)

    abstract override fun hasPermissions(): Boolean

    abstract override fun visualizeImage(image: Image)

    abstract override fun loadImage(imagem: String, imageView: ImageView)
}