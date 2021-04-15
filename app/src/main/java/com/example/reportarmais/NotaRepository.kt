package com.example.reportarmais

import androidx.lifecycle.LiveData

class NotaRepository(private val notaDao: NotaDao) {

    val allItems: LiveData<List<Nota>> = notaDao.getall()

    fun get(id: Long):LiveData<Nota> {
        return notaDao.get(id)
    }

    suspend fun update(item: Nota) {
        notaDao.update(item)
    }

    suspend fun insert(item: Nota) {
        notaDao.insert(item)
    }

    suspend fun delete(item: Nota) {
        notaDao.delete(item)
    }
}