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
                val (idPergunta, resposta, respostas, alternativa, alternativas, imagens) = it
                var dto: RespostaPayload? = null

                if (idPergunta != null) {
                    resposta?.let { dto = RespostaPayload(idPergunta, resposta) }
                    respostas?.let { dto = RespostaPayload(idPergunta, respostas) }
                    alternativa?.let { dto = RespostaPayload(idPergunta, alternativa.id) }

                    alternativas?.let {
                        val alternativasTratadas = alternativas.map { it.toDTO() }
                        dto = RespostaPayload(idPergunta, alternativasTratadas)
                    }

                    imagens?.let {
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