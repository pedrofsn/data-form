package br.redcode.sample.model.repository

import br.redcode.dataform.lib.model.Form
import br.redcode.sample.R
import br.redcode.sample.utils.JSONReader
import com.google.gson.Gson

class InteractorFormRawImpl : InteractorFormRaw {

    override fun loadFormFromJSON(): Form {
        val idJsonRaw = R.raw.questions_1
        val json = JSONReader().getStringFromJson(idJsonRaw)
        val parser = Gson()
        return parser.fromJson(json, Form::class.java)
    }
}