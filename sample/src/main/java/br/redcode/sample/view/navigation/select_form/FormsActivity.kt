package br.redcode.sample.view.navigation.select_form

import br.com.redcode.base.mvvm.extensions.observer
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.sample.R
import br.redcode.sample.databinding.ActivityFormsBinding
import br.redcode.sample.domain.ActivityMVVM
import br.redcode.sample.view.common.AdapterString
import br.redcode.sample.view.navigation.select_form_with_answers.AnsweredActivity

/*
    CREATED BY @PEDROFSN
*/
class FormsActivity : ActivityMVVM<ActivityFormsBinding, FormsViewModel>() {

    override val classViewModel = FormsViewModel::class.java
    override val layout = R.layout.activity_forms

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
        viewModel.load()
    }

    private fun updateUI(label: List<String>) {
        adapter.setCustomList(label)
        hideProgress()
    }

    override fun handleEvent(event: String, obj: Any?) {
        when (event) {
            "open" -> if (obj != null && obj is Long) goTo<AnsweredActivity>("form_id" to obj)
            else -> super.handleEvent(event, obj)
        }
    }

}