package com.example.reportarmais

import android.app.Application
import com.example.reportarmais.db.NotaDB
import com.example.reportarmais.db.NotaRepository

class NotaApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { NotaDB.getDatabase(this) }
    val repository by lazy { NotaRepository(database.notaDao()) }
}