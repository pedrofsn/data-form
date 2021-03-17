package br.redcode.sample.model.repository

import br.com.redcode.base.mvvm.extensions.isValid
import br.redcode.dataform.lib.model.Form
import br.redcode.sample.data.database.MyRoomDatabase

class InteractorFormDatabaseImpl : InteractorFormDatabase {

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