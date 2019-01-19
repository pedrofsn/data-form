package br.redcode.sample.activities

import android.os.Bundle
import br.redcode.dataform.lib.model.FormQuestions
import br.redcode.sample.R
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.domain.ActivityCapturarImagem
import br.redcode.sample.extensions.toEntity
import br.redcode.sample.utils.JSONReader
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ActivityDatabaseInDevelopment : ActivityCapturarImagem(), CoroutineScope {

    private lateinit var formQuestions: FormQuestions
    private val reader by lazy { JSONReader(this) }

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = io()

    fun io() = job + Dispatchers.IO
    fun main() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { launch(io()) { populateFormQuestiosn() } }
    }

    private suspend fun populateFormQuestiosn() = coroutineScope() {
        val json = reader.getStringFromJson(R.raw.perguntas_3)

        val gson = Gson()
        formQuestions = gson.fromJson<FormQuestions>(json, FormQuestions::class.java)

        val fakeEntity = formQuestions.toEntity()
        MyRoomDatabase.getInstance().formQuestionsDAO().insert(fakeEntity)

    }

    private suspend fun seedDatabase() = coroutineScope {
        //        val entityQuestion1 = EntityQuestion(
//                description = "Me descreva em 5 linhas",
//                information = Calendar.getInstance().toString(),
//                format = FORMAT_QUESTION_TEXTUAL_MULTI,
//                type = TYPE_QUESTION_TEXTUAL
//        )
//
//        val id1 = MyRoomDatabase.getInstance().questionDAO().insert(entityQuestion1)
//
//        val entityAnswer1 = EntityAnswer(
//                idQuestion = id1,
//                answer = "Esta é a linha 1\n2\n3\n4\n5"
//        )
//
//        MyRoomDatabase.getInstance().answerDAO().insert(entityAnswer1)
    }

}