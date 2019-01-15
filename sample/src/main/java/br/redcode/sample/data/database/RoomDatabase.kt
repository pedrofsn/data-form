package br.redcode.sample.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import br.redcode.sample.App
import br.redcode.sample.BuildConfig
import br.redcode.sample.data.dao.QuestionDAO
import br.redcode.sample.data.entities.QuestionEntity
import java.util.concurrent.Executors

/**
 * Created by pedrofsn on 28/05/2018.
 */

@Database(
        entities = [
            QuestionEntity::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomDatabase : RoomDatabase() {

    abstract fun questionDAO(): QuestionDAO

    companion object {

        @Volatile
        private var INSTANCE: RoomDatabase? = null
        private val DATABASE_NAME: String = "dataform.db"

        fun getInstance(context: Context = App.getContext()!!) = INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context): RoomDatabase {
            val room = Room.databaseBuilder(context.applicationContext, RoomDatabase::class.java, DATABASE_NAME)
            if (BuildConfig.DEBUG) room.fallbackToDestructiveMigration()
            seedDatabase(room)
            return room.build()
        }

        private fun seedDatabase(room: Builder<RoomDatabase>) {
            room.addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute { Mock.seedDatabase() }
                }
            })
        }
    }
}