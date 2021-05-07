package com.example.reportarmais.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("myslim/index.php/api/allIncidents")
    fun getIncidents(): Call<List<Incident>>

    @GET("myslim/index.php/api/getIncident/{id}")
    fun getIncidentById(@Path("id") first: Int): Call<Incident>

    @FormUrlEncoded
    @POST("/myslim/index.php/api/loginn")
    fun postTest(@Field("username") username: String?, @Field("passwd") passwd: String?): Call<OutputPost>

    @FormUrlEncoded
    @POST("/myslim/index.php/api/deleteIncident")
    fun removeInc(@Field("id") first: Int?): Call<RemoveIncPost>

    @FormUrlEncoded
    @POST("/myslim/index.php/api/updateIncident")
    fun updateInc(@Field("id") id: Int?, @Field("cat") cat: String?,
                  @Field("des") des: String?): Call<RemoveIncPost>

}