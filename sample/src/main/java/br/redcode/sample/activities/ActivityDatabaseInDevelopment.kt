package br.redcode.sample.activities

import android.content.Intent
import android.os.Bundle
import br.redcode.dataform.lib.model.FormQuestions
import br.redcode.dataform.lib.ui.UIForm
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityCapturarImagem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ActivityDatabaseInDevelopment : ActivityCapturarImagem(), CoroutineScope {

    private lateinit var agregador: UIForm
    private lateinit var formQuestions: FormQuestions

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = io()

    fun io() = job + Dispatchers.IO
    fun main() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, ActivityMain::class.java))

        button.setOnClickListener { launch(io()) { seedDatabase() } }
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