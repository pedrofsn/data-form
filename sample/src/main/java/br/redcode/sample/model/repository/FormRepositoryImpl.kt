package br.redcode.sample.model.repository

import br.com.redcode.base.mvvm.extensions.isValid
import br.redcode.dataform.lib.model.Form
import br.redcode.sample.R
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.utils.JSONReader
import com.google.gson.Gson

class FormRepositoryImpl : FormRepository {

    override fun loadFormFromJSON(): Form {
        val idJsonRaw = R.raw.questions_1
        val json = JSONReader().getStringFromJson(idJsonRaw)
        val parser = Gson()
        return parser.fromJson(json, Form::class.java)
    }

    override fun loadFormFromDatabase(idFormAnswers: Long?): Form? {
        return idFormAnswers?.let { form_with_answers_id ->
            MyRoomDatabase.getInstance().formDAO()
                .readFormWithAnswers(form_with_answers_id)
        }
    }

    override fun loadOnlyFormFromDatabase(idForm: Long): Form {
        return MyRoomDatabase.getInstance().formDAO()
            .takeIf { idForm.isValid() }
            ?.readOnlyForm(idForm)
            ?: throw RuntimeException("We need form_id")
    }
}