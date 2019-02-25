package br.redcode.sample.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import br.redcode.sample.App
import br.redcode.sample.BuildConfig
import br.redcode.sample.data.dao.*
import br.redcode.sample.data.entities.*
import java.util.concurrent.Executors

/**
 * Created by pedrofsn on 28/05/2018.
 */

@Database(
        entities = [
            // ANSWER
            EntityAnswer::class,
            EntityAnswerImage::class,
            EntityAnswerOption::class,

            // QUESTION
            EntityQuestion::class,
            EntityQuestionCustomSettings::class,
            EntityQuestionLimit::class,
            EntityQuestionOption::class,

            // FORM
            EntityFormSettings::class,
            EntityForm::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun answerDAO(): AnswerDAO
    abstract fun answerImageDAO(): AnswerImageDAO
    abstract fun answerOptionDAO(): AnswerOptionDAO

    abstract fun questionDAO(): QuestionDAO
    abstract fun questionCustomSettingsDAO(): QuestionCustomSettingsDAO
    abstract fun questionLimitDAO(): QuestionLimitDAO
    abstract fun questionOptionDAO(): QuestionOptionDAO

    abstract fun formSettingsDAO(): FormSettingsDAO
    abstract fun formDAO(): FormDAO

    companion object {

        @Volatile
        private var INSTANCE: MyRoomDatabase? = null
        private const val DATABASE_NAME: String = "dataform.db"

        fun getInstance(context: Context = App.getContext()!!): MyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): MyRoomDatabase {
            val room = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDatabase::class.java,
                    DATABASE_NAME
            )
            if (BuildConfig.DEBUG) room.fallbackToDestructiveMigration()
            seedDatabase(room)
            return room.build()
        }

        private fun seedDatabase(room: Builder<MyRoomDatabase>) {
            room.addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute { Mock.seedDatabase() }
                }
            })
        }
    }
}