package com.moriarity_code.pedalplates.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.moriarity_code.pedalplates.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (Build.VERSION.SDK_INT >= 21)
        {
            window.statusBarColor = ContextCompat.getColor(this@SplashActivity, R.color.statusBar)
            window.navigationBarColor = ContextCompat.getColor(this@SplashActivity, R.color.statusBar)
        }
    }
}
