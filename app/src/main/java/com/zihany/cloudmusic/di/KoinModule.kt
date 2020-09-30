package com.zihany.cloudmusic.di

import com.zihany.cloudmusic.login.mvvm.model.LoginRepository
import com.zihany.cloudmusic.login.mvvm.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}

val repositoryModule = module {
    single { LoginRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)