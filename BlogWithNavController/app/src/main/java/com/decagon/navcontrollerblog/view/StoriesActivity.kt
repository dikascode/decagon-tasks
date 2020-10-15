package com.decagon.navcontrollerblog.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.decagon.navcontrollerblog.R


class StoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)

        //Setup action bar with Nav controller
        setupActionBarWithNavController(findNavController(R.id.fragment))

    }

    //Enable back press button on Navigation
    // 
    // controller menu
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}