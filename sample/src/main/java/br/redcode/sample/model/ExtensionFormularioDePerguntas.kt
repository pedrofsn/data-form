package br.redcode.sample.model

import br.redcode.dataform.lib.extension.toDTO
import br.redcode.dataform.lib.model.FormQuestions
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.payloads.PayloadAnswer

/**
 * Created by pedrofsn on 12/11/2017.
 */


fun FormQuestions.toListDTO(): ArrayList<PayloadAnswer> {
    val dtos = ArrayList<PayloadAnswer>()

    questions.let {

        val respostas = it.filter { question: Question -> question.answer?.hasAnswer() == true }.map { it.answer }

        for (r in respostas) {
            r?.let {
                val (id, answer, options, images) = it
                var dto: PayloadAnswer? = null

                if (id != null) {
                    answer?.let { dto = PayloadAnswer(id, answer) }
                    options?.let { dto = PayloadAnswer(id, options) }

                    options?.let {
                        val alternativasTratadas = options.map { it.toDTO() }
                        dto = PayloadAnswer(id, alternativasTratadas)
                    }

                    images?.let {
                        dto = PayloadAnswer(id, images)
                    }

                    dto?.let { dtos.add(it) }
                }
            }
        }
    }

    return dtos
}