package com.example.week6.ROOM

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id=:id")
    fun findById(id: Int): User

    @Query("SELECT * FROM user WHERE name=:name")
    fun findByName(name: String): User

    @Insert
    fun insertAll(vararg todo: User): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: User): Long

    @Delete
    fun delete(todo: User)

    @Update
    fun update(user: User)

    @Query("DELETE FROM user")
    fun deleteAllTask()
}