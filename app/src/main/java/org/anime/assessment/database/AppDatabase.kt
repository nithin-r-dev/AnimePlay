package org.anime.assessment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.database.classes.AnimeProductionDetails


@Database(
    entities = [AnimeHomeClass::class, AnimeProductionDetails::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AniDao


    companion object {
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "AnimePlayDatabase"
                    )
                        .allowMainThreadQueries()
                        .setJournalMode(JournalMode.WRITE_AHEAD_LOGGING)
                        .build()
                }
            }
            return instance!!
        }

    }

}