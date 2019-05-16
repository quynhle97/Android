package com.example.week6.ROOM

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy

@Dao
interface TaskDAO {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE id=:id")
    fun findById(id: Int): Task

    @Query("SELECT * FROM task WHERE description=:description")
    fun findByDescription(description: String): Task

    @Insert
    fun insertAll(vararg todo: Task): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: Task): Long

    @Delete
    fun delete(todo: Task)

    @Update
    fun update(task: Task)

    @Query("DELETE FROM task")
    fun deleteAllTask()
}