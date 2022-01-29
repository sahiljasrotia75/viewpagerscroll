package com.app.atsz7.viewpagerautoscroll.module

import com.app.atsz7.viewpagerautoscroll.ui.activities.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val viewModelModule: Module = module {
    viewModel { MainViewModel(get()) }
}