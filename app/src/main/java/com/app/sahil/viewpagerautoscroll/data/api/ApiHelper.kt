package com.app.atsz7.viewpagerautoscroll.data.api

import com.app.atsz7.viewpagerautoscroll.data.model.SlideData
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<SlideData>
}