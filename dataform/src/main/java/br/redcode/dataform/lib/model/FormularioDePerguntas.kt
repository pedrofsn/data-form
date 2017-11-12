package br.redcode.dataform.lib.model

import java.io.Serializable

/**
 * Created by pedrofsn on 12/11/2017.
 */
data class FormularioDePerguntas(
        val configuracoes: ConfiguracaoFormulario,
        val perguntas: ArrayList<Pergunta>
) : Serializable

