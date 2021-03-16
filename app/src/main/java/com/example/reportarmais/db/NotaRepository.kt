package com.example.reportarmais.db

import androidx.annotation.WorkerThread
import com.example.reportarmais.dao.NotaDAO
import com.example.reportarmais.entities.Nota
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class NotaRepository(private val notaDAO: NotaDAO) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Nota>> = notaDAO.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(nota: Nota) {
        notaDAO.insert(nota)
    }
}