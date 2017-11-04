package br.redcode.sample.model

import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.payloads.RespostaPayload
import java.io.Serializable

/**
 * Created by pedrofsn on 31/10/2017.
 */
data class MinhasPerguntas(val perguntas: ArrayList<Pergunta>) : Serializable {

    fun toListDTO(): ArrayList<RespostaPayload> {
        val dtos = ArrayList<RespostaPayload>()

        perguntas.let {

            val respostas = it.filter { pergunta: Pergunta -> pergunta.resposta?.hasResposta() == true }.map { it.resposta }

            for (r in respostas) {
                r?.let {
                    val (idPergunta, resposta, alternativa, alternativas, imagens) = it
                    var dto: RespostaPayload? = null

                    if (idPergunta != null) {

                        if (resposta != null) {
                            dto = RespostaPayload(idPergunta, resposta)
                        }

                        if (alternativa != null) {
                            dto = RespostaPayload(idPergunta, alternativa.toDTO())
                        }

                        if (alternativas != null) {
                            val alternativasTratadas = alternativas.map { it.toDTO() }
                            dto = RespostaPayload(idPergunta, alternativasTratadas)
                        }

                        if (imagens != null) {
                            dto = RespostaPayload(idPergunta, imagens)
                        }

                        dto?.let { dtos.add(it) }
                    }
                }
            }
        }

        return dtos
    }

}