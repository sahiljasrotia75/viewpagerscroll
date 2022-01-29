package com.app.atsz7.viewpagerautoscroll.data.api

import com.app.atsz7.viewpagerautoscroll.data.model.SlideData
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<SlideData> = apiService.getUsers()

}