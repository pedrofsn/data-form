package br.redcode.sample.view.main

import android.view.View
import br.redcode.sample.R
import br.redcode.sample.data.database.Mock
import br.redcode.sample.domain.ActivityWithoutMVVM
import br.redcode.sample.view.forms.FormsActivity
import br.redcode.sample.view.questions.QuestionsActivity
import br.redcode.sample.view.questions.QuestionsActivity.Companion.LOAD_FORM_FROM_DATABASE
import br.redcode.sample.view.questions.QuestionsActivity.Companion.LOAD_FORM_FROM_JSON
import kotlinx.coroutines.launch

/*
    CREATED BY @PEDROFSN
*/

class ActivityMain(override val layout: Int = R.layout.activity_main) : ActivityWithoutMVVM() {

    override fun afterOnCreate() {

    }

    fun openCase1(view: View?) = goTo<QuestionsActivity>("case" to LOAD_FORM_FROM_JSON)
    fun openCase2(view: View?) = goTo<FormsActivity>("case" to LOAD_FORM_FROM_DATABASE)

    fun resetDatabase(view: View?) {
        launch(io()) {
            Mock.seedDatabase()

            launch(main()) {
                showMessage("Database has been reseted")
            }
        }
    }

}