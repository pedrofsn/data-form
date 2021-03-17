package br.redcode.sample.model.repository

import br.redcode.dataform.lib.model.Answer
import java.util.*

interface AnswerRepository {

    fun saveAnswer(idForm: Long, idFormAnswers: Long, newAnswer: Answer): Long
    fun deleteAndSave(idForm: Long, idFormAnswers: Long, answers: HashMap<Long, Answer>): Long
}