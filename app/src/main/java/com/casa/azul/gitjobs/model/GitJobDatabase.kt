package com.casa.azul.gitjobs.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(GitJob::class), version = 1)
abstract class GitJobDatabase: RoomDatabase() {
    abstract fun gitjobDao(): GitJobDao

    companion object{
        @Volatile private var instance: GitJobDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GitJobDatabase::class.java,
            "gitjobdatabase"
        ).build()
    }
}