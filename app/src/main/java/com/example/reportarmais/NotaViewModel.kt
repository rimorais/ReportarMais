package com.example.reportarmais

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.reportarmais.NotaDatabase
import com.example.reportarmais.Nota
import com.example.reportarmais.NotaRepository
import kotlinx.coroutines.launch

private const val TAG = "NotaViewModel "

class NotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotaRepository
    val allItems: LiveData<List<Nota>>

    init {
        Log.d(TAG, "Dentro do ViewModel init")
        val dao = NotaDatabase.getDatabase(application).notaDao()
        repository = NotaRepository(dao)
        allItems = repository.allItems
    }

    fun insert(item: Nota) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: Nota) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: Nota) = viewModelScope.launch {
        repository.delete(item)
    }

    fun get(id: Long) = repository.get(id)
}