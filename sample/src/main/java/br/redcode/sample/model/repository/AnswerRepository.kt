package br.redcode.sample.model.repository

import br.redcode.dataform.lib.model.Answer

interface AnswerRepository {

    fun saveAnswer(idForm: Long, idFormAnswers: Long, newAnswer: Answer): Long
}