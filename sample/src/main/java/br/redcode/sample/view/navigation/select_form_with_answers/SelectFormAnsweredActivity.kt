package br.redcode.sample.view.navigation.select_form_with_answers

import android.view.View
import br.com.redcode.base.extensions.receiveLong
import br.com.redcode.base.mvvm.extensions.observer
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.sample.R
import br.redcode.sample.databinding.ActivitySelectFormAnsweredBinding
import br.redcode.sample.domain.ActivityMVVM
import br.redcode.sample.view.common.AdapterString
import br.redcode.sample.view.dynamic_form.form_questions.FormActivity
import br.redcode.sample.view.dynamic_form.form_questions.FormActivity.Companion.LOAD_FORM_WITH_ANSWERS_FROM_DATABASE
import br.redcode.sample.view.dynamic_form.form_questions.FormActivity.Companion.LOAD_ONLY_FORM_FROM_DATABASE

/*
    CREATED BY @PEDROFSN
*/
class SelectFormAnsweredActivity : ActivityMVVM<ActivitySelectFormAnsweredBinding, SelectFormAnsweredViewModel>() {

    override val classViewModel = SelectFormAnsweredViewModel::class.java
    override val layout = R.layout.activity_select_form_answered

    private val idForm by receiveLong("form_id")

    private val adapter = AdapterString { model: String, index: Int -> viewModel.open(index) }
    private val observer = observer<List<String>> { updateUI(it) }

    override fun setupUI() {
        super.setupUI()
        viewModel.liveData.observe(this, observer)
    }

    override fun afterOnCreate() {
        enableHomeAsUpActionBar()
        binding.recyclerView.setCustomAdapter(
                adapter = adapter,
                incluirDivider = true
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.load(idForm)
    }

    private fun updateUI(label: List<String>) {
        adapter.setCustomList(label)
        hideProgress()
    }

    override fun handleEvent(event: String, obj: Any?) {
        when (event) {
            "open" -> if (obj != null && obj is Long) FormActivity.open(
                    context = this,
                    case = LOAD_FORM_WITH_ANSWERS_FROM_DATABASE,
                    idFormAnswers = obj
            )
            else -> super.handleEvent(event, obj)
        }
    }

    fun add(view: View?) = FormActivity.open(
            context = this,
            case = LOAD_ONLY_FORM_FROM_DATABASE,
            idForm = idForm
    )

}