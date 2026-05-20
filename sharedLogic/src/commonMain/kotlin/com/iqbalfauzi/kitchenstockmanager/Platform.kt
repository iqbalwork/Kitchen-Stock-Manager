package com.iqbalfauzi.kitchenstockmanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform