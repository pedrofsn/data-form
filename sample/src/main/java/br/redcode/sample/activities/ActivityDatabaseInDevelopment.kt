package br.redcode.sample.activities

import android.os.Bundle
import br.redcode.dataform.lib.model.FormQuestions
import br.redcode.dataform.lib.ui.UIForm
import br.redcode.dataform.lib.utils.Constants.FORMAT_QUESTION_TEXTUAL_MULTI
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_TEXTUAL
import br.redcode.sample.R
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.data.entities.EntityAnswer
import br.redcode.sample.data.entities.EntityQuestion
import br.redcode.sample.domain.ActivityCapturarImagem
import com.idescout.sql.SqlScoutServer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class ActivityDatabaseInDevelopment : ActivityCapturarImagem(), CoroutineScope {

    private lateinit var agregador: UIForm
    private lateinit var formQuestions: FormQuestions

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = io()

    fun io() = job + Dispatchers.IO
    fun main() = job + Dispatchers.Main

    private val sqlScoutServer by lazy { SqlScoutServer.create(this@ActivityDatabaseInDevelopment, getPackageName()); }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { launch(io()) { seedDatabase() } }
    }

    private suspend fun seedDatabase() = coroutineScope {
        val entityQuestion1 = EntityQuestion(
                description = "Me descreva em 5 linhas",
                information = Calendar.getInstance().toString(),
                format = FORMAT_QUESTION_TEXTUAL_MULTI,
                type = TYPE_QUESTION_TEXTUAL
        )

        val id1 = MyRoomDatabase.getInstance().questionDAO().insert(entityQuestion1)

        val entityAnswer1 = EntityAnswer(
                idQuestion = id1,
                answer = "Esta é a linha 1\n2\n3\n4\n5"
        )

        MyRoomDatabase.getInstance().answerDAO().insert(entityAnswer1)
    }

    override fun onResume() {
        sqlScoutServer.resume()
        super.onResume()
    }

    override fun onPause() {
        sqlScoutServer.destroy()
        super.onPause()
    }

    override fun onStop() {
        sqlScoutServer.destroy()
        super.onStop()
    }

    override fun onDestroy() {
        sqlScoutServer.destroy()
        super.onDestroy()
    }


}