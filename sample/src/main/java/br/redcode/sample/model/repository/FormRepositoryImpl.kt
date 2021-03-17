package br.redcode.sample.model.repository

import br.redcode.dataform.lib.model.Form
import br.redcode.sample.R
import br.redcode.sample.model.LoadType

class FormRepositoryImpl : FormRepository {

    private val interactorFormRaw by lazy { InteractorFormRawImpl() }
    private val interactorFormDatabase by lazy { InteractorFormDatabaseImpl() }

    override fun getForm(@LoadType case: Int, idFormAnswers: Long, idForm: Long): Form? {
        val form = when (case) {
            LoadType.JSON -> interactorFormRaw.loadFormFromJSON()
            LoadType.FORM_WITH_ANSWERS_FROM_DATABASE -> {
                interactorFormDatabase.loadFormFromDatabase(idFormAnswers)
            }
            LoadType.FORM_FROM_DATABASE -> interactorFormDatabase.loadOnlyFormFromDatabase(idForm)
            else -> throw RuntimeException("Wrong paramenter brow!")
        }
        return customizeDesign(form)
    }

    private fun customizeDesign(form: Form?): Form? = form?.apply {
        settings.idLayoutWrapper = R.layout.ui_question_wrapper_like_ios
    }
}