package com.example.reportarmais.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("myslim/index.php/api/allIncidents")
    fun getIncidents(): Call<List<Incident>>
}