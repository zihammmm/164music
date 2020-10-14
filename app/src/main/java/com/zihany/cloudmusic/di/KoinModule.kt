package com.zihany.cloudmusic.di

import com.zihany.cloudmusic.login.mvvm.model.LoginRepository
import com.zihany.cloudmusic.login.mvvm.viewmodel.LoginViewModel
import com.zihany.cloudmusic.main.mvvm.model.*
import com.zihany.cloudmusic.main.mvvm.viewmodel.MainViewModel
import com.zihany.cloudmusic.main.mvvm.viewmodel.MineViewModel
import com.zihany.cloudmusic.main.mvvm.viewmodel.PlayListViewModel
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { MineViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { WowViewModel(get()) }
    viewModel { PlayListViewModel(get()) }
}

val repositoryModule = module {
    single { LoginRepository() }
    single { MineRepository() }
    single { MainRepository() }
    single { UserPlayListRepository() }
    single { WowRepository() }
    single { LogoutRepository() }
    single { LikeListRepository() }
    single { IntelligenceListRepository() }
    single { CloudVillageRepository() }
    single { PlayListRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)