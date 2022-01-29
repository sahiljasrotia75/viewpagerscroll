package com.app.atsz7.viewpagerautoscroll.utils

import com.app.atsz7.viewpagerautoscroll.extensions.ErrorResponse

data class Resources<out T>(val status: Status, val data: T?, val message: ErrorResponse?) {

    companion object {

        fun <T> success(data: T?): Resources<T> {
            return Resources(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: ErrorResponse?=null, data: T?=null): Resources<T> {
            return Resources(Status.ERROR, data, msg)
        }

        fun <T> loading(): Resources<T> = Resources(Status.LOADING, null, null)
    }

}

enum class Status{
    SUCCESS,ERROR,LOADING
}
