package br.redcode.sample.view.navigation.select_form_with_answers

import br.com.redcode.base.utils.Constants.INVALID_VALUE
import br.redcode.dataform.lib.model.FormAnswered
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.domain.BaseViewModelWithLiveData
import br.redcode.sample.extensions.showProgressbar
import kotlinx.coroutines.launch

class SelectFormAnsweredViewModel : BaseViewModelWithLiveData<List<String>>() {

    private val dao by lazy { MyRoomDatabase.getInstance().formAnsweredDAO() }
    private val results = arrayListOf<FormAnswered>()

    private var idForm = INVALID_VALUE.toLong()

    fun load(idForm: Long) {
        this.idForm = idForm
        load()
    }

    override fun load() = showProgressbar {
        launch(io()) {
            val models = dao.readAllByIdForm(idForm = idForm).map { it.toModel() }

            if (models.isNotEmpty()) {
                results.addAll(models)
            }

            val results = models.map {
                "ID: ${it.id}\nFORM_ID: ${it.idForm}\nLAST_UPDATE: ${it.lastUpdate}"
            }

            liveData.postValue(results)
        }
    }

    fun open(index: Int) = sendEventToUI("open", results[index].id)
}