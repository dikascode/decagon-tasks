package com.decagon.mvvmstories.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.decagon.mvvmstories.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            startActivity(Intent(this, StoriesActivity::class.java))
            finish()
        }, 2000)

    }
}