package br.redcode.dataform.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConfiguracaoFormulario(
        val exibirIndicadorErro: Boolean = true,
        val exibirIndicadorInformacao: Boolean = true,
        val editavel: Boolean = true,
        val corBackgroundFormulario: String? = null
) : Parcelable {

    fun hasIndicadorVisivel() = exibirIndicadorErro or exibirIndicadorInformacao

}