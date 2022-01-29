package com.app.atsz7.viewpagerautoscroll.extensions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable



open class BaseViewModel: ViewModel()
{
    val loader: MutableLiveData<Boolean> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}