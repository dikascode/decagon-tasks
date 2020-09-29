package com.decagon.navcontrollerblog.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.decagon.navcontrollerblog.R
import com.decagon.navcontrollerblog.data.BlogDatabase
import com.decagon.navcontrollerblog.data.Stories
import com.decagon.navcontrollerblog.network.RetroInstance
import com.decagon.navcontrollerblog.network.RetroService
import com.decagon.navcontrollerblog.recycler_interface.RecyclerViewClickInterface
import com.decagon.navcontrollerblog.repository.RoomRepositoryImpl
import com.decagon.navcontrollerblog.view.adapter.PostsListAdapter
import com.decagon.navcontrollerblog.viewmodel.RoomCommentViewModel
import com.decagon.navcontrollerblog.viewmodel.RoomStoryViewModel
import com.decagon.navcontrollerblog.viewmodel.ViewModelFactory

class StoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)

        //Setup action bar with Nav controller
        setupActionBarWithNavController(findNavController(R.id.fragment))

//        commentViewModel = ViewModelProvider(this, viewModelFactory).get(RoomCommentViewModel::class.java)
//        commentViewModel

    }

    //Enable back press button on Navigation controller menu
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}