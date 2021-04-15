package com.example.reportarmais

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotaDao {

    @Query("SELECT * from tabelanotas")
    fun getall(): LiveData<List<Nota>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: Nota)

    @Query("SELECT * FROM tabelanotas WHERE tabelanotas.id == :id")
    fun get(id: Long): LiveData<Nota>

    @Update
    suspend fun update(vararg items: Nota)

    @Delete
    suspend fun delete(vararg items: Nota)
}