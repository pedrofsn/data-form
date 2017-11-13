package br.redcode.dataform.lib.model

import java.io.Serializable

/**
 * Created by pedrofsn on 13/11/2017.
 */
data class InputPopup(
        val titulo: String,
        val campos: List<Campo>
) : Serializable