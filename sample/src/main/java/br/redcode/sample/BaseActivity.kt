package br.redcode.sample

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.widget.Toast
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ActivityCapturarImagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem
import org.jetbrains.anko.intentFor
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

/**
 * Created by pedrofsn on 03/11/2017.
 */
abstract class BaseActivity : ActivityCapturarImagem(), EasyImage.Callbacks {

    private val RESULT_CODE_EASY_IMAGE = 1992
    private val RESULT_CODE_PERMISSAO = 12

    override fun previsualizarImagem(caminho: String) {
        startActivity(intentFor<ActivityImagemComZoom>("imagem" to caminho))
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

    override fun capturarImagemComContext(tipo: UIPerguntaImagem.Tipo) {
        when (tipo) {
            UIPerguntaImagem.Tipo.CAMERA_OU_GALERIA -> EasyImage.openChooserWithGallery(this, getString(R.string.selecione), RESULT_CODE_EASY_IMAGE)
            UIPerguntaImagem.Tipo.CAMERA -> EasyImage.openCamera(this, RESULT_CODE_EASY_IMAGE)
            UIPerguntaImagem.Tipo.GALERIA -> EasyImage.openGallery(this, RESULT_CODE_EASY_IMAGE)
        }
    }

    override fun hasTodasPermissoesAtivas(): Boolean {
        for (permissao in permissoes) {
            val isOk: Boolean = ContextCompat.checkSelfPermission(this@BaseActivity, permissao) == PermissionChecker.PERMISSION_GRANTED

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
        Toast.makeText(this@BaseActivity, getString(R.string.habilite_todas_as_permissoes), Toast.LENGTH_LONG).show()
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

}