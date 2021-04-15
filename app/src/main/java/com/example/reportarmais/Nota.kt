package com.example.reportarmais

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabelanotas")
data class Nota(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val texto: String
)