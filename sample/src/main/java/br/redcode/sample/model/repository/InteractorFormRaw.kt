package br.redcode.sample.model.repository

import br.redcode.dataform.lib.model.Form

interface InteractorFormRaw {
    fun loadFormFromJSON(): Form
}