package com.iqbalfauzi.kitchenstockmanager.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoinLogic(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule)
    }
