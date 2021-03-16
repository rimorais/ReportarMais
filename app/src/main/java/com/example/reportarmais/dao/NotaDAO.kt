package com.example.reportarmais.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reportarmais.entities.Nota

@Dao

interface WordDao{

    @Query("SELECT * FROM nota_table ORDER BY data ASC")
    fun getAlphabetizedWords(): List<Nota>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nota: Nota)

    @Query("DELETE FROM nota_table")
    suspend fun deleteAll()

}