package br.redcode.sample.model

import br.redcode.dataform.lib.model.FormularioDePerguntas
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.payloads.RespostaPayload

/**
 * Created by pedrofsn on 12/11/2017.
 */


fun FormularioDePerguntas.toListDTO(): ArrayList<RespostaPayload> {
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
                        dto = RespostaPayload(idPergunta, alternativa.id)
                    }

                    if (alternativas != null) {
                        val alternativasTratadas = alternativas.map { it.toDTO() }
                        dto = RespostaPayload(idPergunta, alternativasTratadas)
                    }

                    if (imagens != null) {
                        val imagensTratadas = imagens.map { it.imagem }
                        dto = RespostaPayload(idPergunta, imagensTratadas)
                    }

                    dto?.let { dtos.add(it) }
                }
            }
        }
    }

    return dtos
}