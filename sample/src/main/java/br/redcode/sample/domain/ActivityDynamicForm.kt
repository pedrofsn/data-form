package br.redcode.sample.domain

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
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
import androidx.databinding.ViewDataBinding
import br.com.redcode.base.extensions.toLogcat
import br.com.redcode.easyglide.library.load
import br.redcode.dataform.lib.domain.handlers.HandlerCaptureImage
import br.redcode.dataform.lib.interfaces.ImageCapturable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.model.Image
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.ui.UIForm
import br.redcode.dataform.lib.ui.UIQuestionImage
import br.redcode.sample.R
import br.redcode.sample.view.image.ImageZoomActivity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

/*
    CREATED BY @PEDROFSN
*/

abstract class ActivityDynamicForm<B : ViewDataBinding, VM : BaseViewModel> : ActivityMVVM<B, VM>(), EasyImage.Callbacks {

    companion object {
        const val REQUEST_CODE_ANSWER = 26

        private val RESULT_CODE_PERMISSION = 27
        private val RESULT_CODE_EASY_IMAGE = 28
        private val permissions: Array<String> = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    lateinit var uiForm: UIForm

    abstract val onQuestionClicked: ((Question) -> Unit)?

    private val imageCapturable = object : ImageCapturable {

        override fun captureImage(type: UIQuestionImage.Type) {
            when (type) {
                UIQuestionImage.Type.CAMERA_OR_GALLERY -> EasyImage.openChooserWithGallery(getMyActivity(), getString(R.string.select), RESULT_CODE_EASY_IMAGE)
                UIQuestionImage.Type.CAMERA -> EasyImage.openCameraForImage(getMyActivity(), RESULT_CODE_EASY_IMAGE)
                UIQuestionImage.Type.GALLERY -> EasyImage.openGallery(getMyActivity(), RESULT_CODE_EASY_IMAGE)
            }
        }

        override fun hasPermissions(): Boolean {
            for (permission in permissions) {
                val isOk: Boolean = ContextCompat.checkSelfPermission(getMyActivity(), permission) == PermissionChecker.PERMISSION_GRANTED

                if (isOk.not()) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getMyActivity(), permission)) {
                        forcePermissions()
                    } else {
                        ActivityCompat.requestPermissions(getMyActivity(), permissions, RESULT_CODE_PERMISSION)
                    }
                    return false
                }
            }

            return true
        }

        override fun loadImage(imagem: String, imageView: ImageView) {
            var temp = imagem

            if (imagem.startsWith("/")) {
                temp = "file://$imagem"
            }

            "Loading image '$temp'".toLogcat()

            imageView.load(temp)
        }

        override fun visualizeImage(image: Image) = goTo<ImageZoomActivity>("image" to image.image)
    }

    private fun getMyActivity(): Activity = this@ActivityDynamicForm

    val handlerCaptureImage by lazy { HandlerCaptureImage(imageCapturable) }

    private fun forcePermissions() {
        Toast.makeText(this, getString(R.string.enable_all_permissions), Toast.LENGTH_LONG).show()
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RESULT_CODE_PERMISSION -> {
                if ((grantResults.filter { idPermissao: Int -> idPermissao == PackageManager.PERMISSION_GRANTED }.size == permissions.size).not()) {
                    forcePermissions()
                }
                return
            }
        }
    }

    fun fillAnswers(answers: List<Answer>) {
        launch(main()) { uiForm.fillAnswers(answers) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_CODE_ANSWER -> {
                    val answer = data.getParcelableExtra<Answer>("answer")

                    if (answer != null) {
                        updateAnswer(answer)
                        fillAnswers()
                    }
                }
                else -> EasyImage.handleActivityResult(requestCode, resultCode, data, this, this)
            }
        }
    }

    abstract fun updateAnswer(answer: Answer)

    abstract fun fillAnswers()

    fun updateUIForm(form: Form) {
        launch(main()) {
            uiForm = UIForm(form, handlerCaptureImage, onQuestionClicked)
            val view = uiForm.getViewGenerated(this@ActivityDynamicForm)

            getViewGroupToHandleForm()?.addView(view)

            fillAnswers(form.answers)
        }
    }

    abstract fun getViewGroupToHandleForm(): ViewGroup?

    override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
        e?.let {
            it.printStackTrace()
            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
        imagesFiles.forEach { getDialogDialogComOk(file = it, handlerCaptureImage = handlerCaptureImage) }
    }

    private fun getDialogDialogComOk(context: Context = this, file: File, handlerCaptureImage: HandlerCaptureImage) {
        val viewDialog = (context as AppCompatActivity).layoutInflater.inflate(R.layout.dialog_imagem, null)

        val imageViewPreview: ImageView = viewDialog.findViewById(R.id.imageViewPreview)
        val editTextLegenda: EditText = viewDialog.findViewById(R.id.editTextLegenda)
        val buttonOk: Button = viewDialog.findViewById(R.id.buttonOk)

        editTextLegenda.hint = "Apenas em uma pergunta"

        imageCapturable.loadImage(file.absolutePath, imageViewPreview)

        val alert = AlertDialog.Builder(context /*, R.style.DialogCustomizado*/)
        alert.setView(viewDialog)
        alert.setCancelable(false)

        val dialog = alert.create()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        buttonOk.setOnClickListener {
            dialog?.dismiss()
            val image = Image(subtitle = editTextLegenda.text.toString(), image = file.absolutePath)
            handlerCaptureImage.onImageSelecteds(image)
        }
    }

    override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {

    }

    fun save(view: View?) {
        launch(main()) {
            val asyncAnswers = async(io()) { uiForm.getAnswers() }
            val answers = asyncAnswers.await()
            handleAnswers(answers)
        }
    }

    open fun handleAnswers(answers: List<Answer>) {

    }
}