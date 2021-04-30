package com.example.reportarmais.api

data class Incident(
    val id: Int,
    val descrip: String,
    val lat: String,
    val lon: String,
    val usernm: Int
)