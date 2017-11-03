package br.redcode.dataform.lib.domain

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.interfaces.OnCapturaImagem
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem
import br.redcode.dataform.lib.utils.Constantes
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


/**
 * Created by pedrofsn on 02/11/2017.
 */
abstract class ActivityCapturarImagem : AppCompatActivity(), EasyImage.Callbacks {

    private val permissoes: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val RESULT_CODE_PERMISSAO = 12

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

    private fun hasTodasPermissoesAtivas(): Boolean {
        for (permissao in permissoes) {
            val isOk: Boolean = ContextCompat.checkSelfPermission(this@ActivityCapturarImagem, permissao) == PermissionChecker.PERMISSION_GRANTED

            if (isOk.not()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissao)) {
                    ActivityCompat.requestPermissions(this, permissoes, RESULT_CODE_PERMISSAO)
                } else {
                    forcarPermissoes()
                }
                return false
            }

        }

        return true
    }

    abstract fun previsualizarImagem(caminho: String)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RESULT_CODE_PERMISSAO -> {
                if ((grantResults.filter { idPermissao: Int -> idPermissao == PackageManager.PERMISSION_GRANTED }.size == permissoes.size).not()) {
                    forcarPermissoes()
                }
                return
            }
        }
    }

    private fun forcarPermissoes() {
        Toast.makeText(this@ActivityCapturarImagem, getString(R.string.habilite_todas_as_permissoes), Toast.LENGTH_LONG).show()
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

}