package br.redcode.sample.domain

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import br.com.redcode.easyglide.library.load
import br.redcode.dataform.lib.domain.ActivityCaptureImage
import br.redcode.dataform.lib.domain.handlers.HandlerCaptureImage
import br.redcode.dataform.lib.model.Image
import br.redcode.dataform.lib.ui.UIQuestionImage
import br.redcode.sample.R
import br.redcode.sample.activities.ActivityImagemComZoom
import br.redcode.sample.utils.Utils
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

/**
 * Created by pedrofsn on 03/11/2017.
 */
abstract class ActivityCapturarImagem : ActivityCaptureImage(), EasyImage.Callbacks {

    private val RESULT_CODE_EASY_IMAGE = 1992
    private val RESULT_CODE_PERMISSAO = 12

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
        imagesFiles.forEach { getDialogDialogComOk(file = it, handlerCaptureImage = handlerCapturaImagem) }
    }

    fun getDialogDialogComOk(context: Context = this, file: File, handlerCaptureImage: HandlerCaptureImage) {
        val viewDialog = (context as AppCompatActivity).layoutInflater.inflate(R.layout.dialog_imagem, null)

        val imageViewPreview: ImageView = viewDialog.findViewById(R.id.imageViewPreview);
        val editTextLegenda: EditText = viewDialog.findViewById(R.id.editTextLegenda);
        val buttonOk: Button = viewDialog.findViewById(R.id.buttonOk);

        editTextLegenda.setHint("Apenas em uma pergunta")

        loadImage(file.absolutePath, imageViewPreview)

        val alert = AlertDialog.Builder(context /*, R.style.DialogCustomizado*/)
        alert.setView(viewDialog)
        alert.setCancelable(false)

        val dialog = alert.create()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        buttonOk.setOnClickListener {
            dialog?.dismiss()
            val imagem = Image(subtitle = editTextLegenda.text.toString(), image = file.absolutePath)
            handlerCaptureImage.onImagensSelecionadas(imagem)
        }
    }

    override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {

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
        Toast.makeText(this@ActivityCapturarImagem, getString(R.string.habilite_todas_as_permissoes), Toast.LENGTH_LONG).show()
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun captureImage(tipo: UIQuestionImage.Tipo) {
        when (tipo) {
            UIQuestionImage.Tipo.CAMERA_OU_GALERIA -> EasyImage.openChooserWithGallery(this, getString(R.string.selecione), RESULT_CODE_EASY_IMAGE)
            UIQuestionImage.Tipo.CAMERA -> EasyImage.openCameraForImage(this, RESULT_CODE_EASY_IMAGE)
            UIQuestionImage.Tipo.GALERIA -> EasyImage.openGallery(this, RESULT_CODE_EASY_IMAGE)
        }
    }

    override fun hasPermissions(): Boolean {
        for (permissao in permissoes) {
            val isOk: Boolean = ContextCompat.checkSelfPermission(this@ActivityCapturarImagem, permissao) == PermissionChecker.PERMISSION_GRANTED

            if (isOk.not()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissao)) {
                    forcarPermissoes()
                } else {
                    ActivityCompat.requestPermissions(this, permissoes, RESULT_CODE_PERMISSAO)
                }
                return false
            }
        }

        return true
    }

    override fun visualizeImage(image: Image) {
        val intent = Intent(this, ActivityImagemComZoom::class.java)
        intent.putExtra("image", image.image)
        startActivity(intent)
    }

    override fun loadImage(imagem: String, imageView: ImageView) {
        var temp = imagem

        if (imagem.startsWith("/")) {
            temp = "file://" + imagem
        }

        Utils.log("Carregando: " + temp)

        imageView.load(temp)
    }

}