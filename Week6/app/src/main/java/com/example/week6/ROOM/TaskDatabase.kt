package com.example.week6.ROOM

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.week6.*

@Database(entities = arrayOf(Task::class), version = 3)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDAO(): TaskDAO

    companion object {
        @Volatile
        private var instance: TaskDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            TaskDatabase::class.java, DATABASE_TASK_NAME
        ).allowMainThreadQueries()
            .build()
    }
}