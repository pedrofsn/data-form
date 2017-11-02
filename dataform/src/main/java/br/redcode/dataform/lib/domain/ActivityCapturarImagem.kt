package br.redcode.dataform.lib.domain

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


/**
 * Created by pedrofsn on 02/11/2017.
 */
abstract class ActivityCapturarImagem : AppCompatActivity(), EasyImage.Callbacks {

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

    }

    override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {

    }
}