package br.redcode.sample.model

import br.redcode.dataform.lib.model.Pergunta
import java.io.Serializable

/**
 * Created by pedrofsn on 31/10/2017.
 */
data class MinhasPerguntas(val perguntas: ArrayList<Pergunta>) : Serializable