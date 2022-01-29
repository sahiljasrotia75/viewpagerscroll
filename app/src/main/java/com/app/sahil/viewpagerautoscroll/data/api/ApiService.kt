package com.app.atsz7.viewpagerautoscroll.data.api

import com.app.atsz7.viewpagerautoscroll.data.model.SlideData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("fjaqJ")
    suspend fun getUsers(): Response<SlideData>

    @GET("fjaqJ")
    fun getJson(): Call<JsonObject?>?


}