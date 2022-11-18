package com.lssoft2022.letsapp

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class MyApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        // 카카오 SDk 초기화
        KakaoSdk.init(this,"b82c8ec08faf228ee0f0a57642cf4075")
    }
}