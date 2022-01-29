package com.app.atsz7.viewpagerautoscroll.ui.activities.viewmodel

import android.R
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.*
import com.app.atsz7.viewpagerautoscroll.data.model.SlideData
import com.app.atsz7.viewpagerautoscroll.data.repository.MainRepository
import com.app.atsz7.viewpagerautoscroll.extensions.BaseViewModel
import com.app.atsz7.viewpagerautoscroll.extensions.ErrorResponse
import com.app.atsz7.viewpagerautoscroll.utils.Resources
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback


class MainViewModel(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<Resources<SlideData>>()
    val users: LiveData<Resources<SlideData>>
        get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            runCatching {
                _users.postValue(Resources.loading())
                mainRepository.getUsers()
            }.onSuccess{
              //  Log.d("myResponse",it.toString())
                _users.postValue(Resources.success(it.body()))
            }.onFailure {
                _users.postValue(
                    Resources.error(
                        ErrorResponse(903,it.message.toString()),null)
                )
            }
        }
    }
}