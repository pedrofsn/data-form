package br.redcode.dataform.lib.domain

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.interfaces.OnCapturaImagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem
import br.redcode.dataform.lib.utils.Constantes
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


/**
 * Created by pedrofsn on 02/11/2017.
 */
abstract class ActivityCapturarImagem : AppCompatActivity(), EasyImage.Callbacks {

    val handlerCapturaImagem = HandlerCapturaImagem(object : OnCapturaImagem {
        override fun capturarImagem(tipo: UIPerguntaImagem.Tipo) {
            capturarImagemComContext(tipo)
        }
    })

    private fun capturarImagemComContext(tipo: UIPerguntaImagem.Tipo) {
        when (tipo) {
            UIPerguntaImagem.Tipo.CAMERA_OU_GALERIA -> EasyImage.openChooserWithGallery(this, getString(R.string.selecione), Constantes.EASY_IMAGE)
            UIPerguntaImagem.Tipo.CAMERA -> EasyImage.openCamera(this, Constantes.EASY_IMAGE)
            UIPerguntaImagem.Tipo.GALERIA -> EasyImage.openGallery(this, Constantes.EASY_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, this)
    }

    override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
        e?.let {
            it.printStackTrace()
            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
        handlerCapturaImagem.onImagensSelecionadas(imagesFiles)
    }

    override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {

    }
}