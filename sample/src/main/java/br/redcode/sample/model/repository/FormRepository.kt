package br.redcode.sample.model.repository

import br.redcode.dataform.lib.model.Form

interface FormRepository {

    fun loadFormFromJSON(): Form
    fun loadFormFromDatabase(idFormAnswers: Long?): Form?
    fun loadOnlyFormFromDatabase(idForm: Long): Form
}