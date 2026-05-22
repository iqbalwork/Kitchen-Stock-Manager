package com.iqbalfauzi.kitchenstockmanager

import android.app.Application
import com.iqbalfauzi.kitchenstockmanager.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class KitchenStockApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        initKoin {
            androidLogger()
            androidContext(this@KitchenStockApplication)
        }
        
        initNapier()
    }
}
