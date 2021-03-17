package br.redcode.sample.model.repository

import br.redcode.dataform.lib.model.Form
import br.redcode.sample.model.LoadType

interface FormRepository {

    fun getForm(@LoadType case: Int, idFormAnswers: Long, idForm: Long): Form?
}