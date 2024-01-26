package com.wewrite.android

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    companion object {
        @Volatile
        private lateinit var application: GlobalApplication

        fun applicationContext(): Context {
            synchronized(this) {
                if (!::application.isInitialized) {
                    throw IllegalStateException("Application not initialized")
                }
                return application.applicationContext
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.kakao_app_key)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        synchronized(this) {
            application = this
        }
    }
}


