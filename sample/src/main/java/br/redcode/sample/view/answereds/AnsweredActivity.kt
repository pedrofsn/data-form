package br.redcode.sample.view.answereds

import br.com.redcode.base.extensions.receiveLong
import br.com.redcode.base.mvvm.extensions.observer
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.sample.R
import br.redcode.sample.databinding.ActivityAnsweredBinding
import br.redcode.sample.domain.ActivityMVVM
import br.redcode.sample.view.answereds.list.AdapterString
import br.redcode.sample.view.questions.QuestionsActivity
import br.redcode.sample.view.questions.QuestionsActivity.Companion.LOAD_FORM_FROM_DATABASE

/*
    CREATED BY @PEDROFSN
*/
class AnsweredActivity : ActivityMVVM<ActivityAnsweredBinding, AnsweredViewModel>() {

    override val classViewModel = AnsweredViewModel::class.java
    override val layout = R.layout.activity_answered

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
            "open" -> if (obj != null && obj is Long) goTo<QuestionsActivity>("idFormAnswers" to obj, "case" to LOAD_FORM_FROM_DATABASE)
            else -> super.handleEvent(event, obj)
        }
    }

}