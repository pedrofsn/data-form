package br.redcode.sample.utils

import android.util.Log.e

/**
 * Created by pedrofsn on 02/11/2017.
 */
object Utils {

    fun log(mensagem: String) {
        e("minha-tag", mensagem)
    }

    const val CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CPF = "cpf"
    const val CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CNPJ = "cnpj"
}