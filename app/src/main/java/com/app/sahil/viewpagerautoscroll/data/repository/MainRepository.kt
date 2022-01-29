package com.app.atsz7.viewpagerautoscroll.data.repository

import com.app.atsz7.viewpagerautoscroll.data.api.ApiHelper
import com.app.atsz7.viewpagerautoscroll.data.api.ApiService

class MainRepository (private val apiHelper: ApiService) {

  /*  suspend fun getUsers() : Resources<List<SlideData>> {
        val res= safeApiCall {
            apiHelper.getUsers()
        }
        return res
    }*/

    suspend fun getUsers() =  apiHelper.getUsers()

}