package br.redcode.dataform.lib.model

import java.io.Serializable

/**
 * Created by pedrofsn on 31/10/2017.
 */
data class Alternativa(
        val id: Int,
        val descricao: String,
        val selecionada: Boolean = false
) : Serializable