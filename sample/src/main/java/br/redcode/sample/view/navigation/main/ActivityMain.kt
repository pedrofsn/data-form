package br.redcode.sample.view.navigation.main

import android.view.View
import br.redcode.sample.R
import br.redcode.sample.data.database.Mock
import br.redcode.sample.domain.ActivityWithoutMVVM
import br.redcode.sample.view.dynamic_form.form_questions.FormActivity
import br.redcode.sample.view.dynamic_form.form_questions.FormActivity.Companion.LOAD_FORM_FROM_DATABASE
import br.redcode.sample.view.dynamic_form.form_questions.FormActivity.Companion.LOAD_FORM_FROM_JSON
import br.redcode.sample.view.navigation.select_form.SelectFormActivity
import kotlinx.coroutines.launch

/*
    CREATED BY @PEDROFSN
*/

class ActivityMain(override val layout: Int = R.layout.activity_main) : ActivityWithoutMVVM() {

    override fun afterOnCreate() {

    }

    fun openCase1(view: View?) = goTo<FormActivity>("case" to LOAD_FORM_FROM_JSON)
    fun openCase2(view: View?) = goTo<SelectFormActivity>("case" to LOAD_FORM_FROM_DATABASE)

    fun resetDatabase(view: View?) {
        launch(io()) {
            Mock.seedDatabase()

            launch(main()) {
                showMessage("Database has been reseted")
            }
        }
    }

}