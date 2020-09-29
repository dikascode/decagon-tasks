package com.decagon.navcontrollerblog.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.decagon.navcontrollerblog.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Splash screen implementation
         */
        Handler().postDelayed({
            startActivity(Intent(this, StoriesActivity::class.java))
            finish()
        }, 2000)
    }
}