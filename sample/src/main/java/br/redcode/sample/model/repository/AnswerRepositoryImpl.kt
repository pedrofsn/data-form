package br.redcode.sample.model.repository

import br.redcode.dataform.lib.model.Answer
import br.redcode.sample.data.database.MyRoomDatabase

class AnswerRepositoryImpl : AnswerRepository {

    override fun saveAnswer(idForm: Long, idFormAnswers: Long, newAnswer: Answer): Long {
        return MyRoomDatabase.getInstance().answerDAO().deleteAndSave(
            form_id = idForm,
            form_with_answers_id = idFormAnswers,
            idQuestion = newAnswer.idQuestion,
            answer = newAnswer
        )
    }
}