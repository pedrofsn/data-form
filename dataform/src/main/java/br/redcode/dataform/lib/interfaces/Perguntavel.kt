package br.redcode.dataform.lib.interfaces

import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
interface Perguntavel {

    fun getResposta(): Resposta

    fun isPreenchidoCorretamente(): Boolean

}