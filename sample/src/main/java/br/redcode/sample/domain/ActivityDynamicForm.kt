package br.redcode.sample.domain

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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
import kotlinx.coroutines.launch
import pl.aprilapps.easyphotopicker.EasyImage

/*
    CREATED BY @PEDROFSN
*/

abstract class ActivityDynamicForm<B : ViewDataBinding, VM : BaseViewModel> : ActivityMVVM<B, VM>() {

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
                UIQuestionImage.Type.CAMERA_OR_GALLERY -> EasyImage.openChooserWithGallery(getMyActivity(), getString(R.string.selecione), RESULT_CODE_EASY_IMAGE)
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
        Toast.makeText(this, getString(R.string.habilite_todas_as_permissoes), Toast.LENGTH_LONG).show()
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
}