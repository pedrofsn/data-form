package br.redcode.sample.view.navigation.select_form

import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.domain.BaseViewModelWithLiveData
import br.redcode.sample.extensions.showProgressbar
import br.redcode.sample.model.MiniForm
import kotlinx.coroutines.launch

class SelectFormViewModel : BaseViewModelWithLiveData<List<String>>() {

    private val dao by lazy { MyRoomDatabase.getInstance().formDAO() }
    private val results = arrayListOf<MiniForm>()

    override fun load() = showProgressbar {
        launch(io()) {
            val models = dao.readAll().map { it.toModel() }

            if (models.isNotEmpty()) {
                results.addAll(models)
            }

            val results = models.map {
                "FORM_ID: ${it.id}\nLAST_UPDATE: ${it.lastUpdate}"
            }

            liveData.postValue(results)
        }
    }

    fun open(index: Int) = sendEventToUI("open", results[index].id)

}