package com.example.reportarmais.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("myslim/index.php/api/allIncidents")
    fun getIncidents(): Call<List<Incident>>

    @FormUrlEncoded
    @POST("/myslim/index.php/api/loginn")
    fun postTest(@Field("validar") first: String?): Call<OutputPost>

    @FormUrlEncoded
    @POST("/myslim/index.php/api/deleteIncident")
    fun removeInc(@Field("id") first: Int?): Call<RemoveIncPost>

}