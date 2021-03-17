package br.redcode.sample.model.repository

import br.redcode.dataform.lib.model.Form

interface InteractorFormDatabase {

    fun loadFormFromDatabase(idFormAnswers: Long?): Form?
    fun loadOnlyFormFromDatabase(idForm: Long): Form
}