package br.redcode.dataform.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by pedrofsn on 12/11/2017.
 */
@Parcelize
data class FormularioDePerguntas(
        val configuracoes: ConfiguracaoFormulario,
        val perguntas: ArrayList<Pergunta>
) : Parcelable

