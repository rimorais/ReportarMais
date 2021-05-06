package com.example.reportarmais.api

data class Incident(
    val id: Int,
    val cat: String,
    val lat: String,
    val lon: String,
    val usernm: String,
    val des: String
)