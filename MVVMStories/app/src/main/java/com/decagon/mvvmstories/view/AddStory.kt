package com.decagon.mvvmstories.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvvmstories.R
import com.decagon.mvvmstories.data.Stories
import com.decagon.mvvmstories.repository.RoomStoryFactory
import com.decagon.mvvmstories.viewModel.RoomStoryViewModel
import kotlinx.android.synthetic.main.activity_add_story.*

class AddStory : AppCompatActivity() {
    private lateinit var viewModel: RoomStoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_story)

        val factory = RoomStoryFactory(application)

        viewModel = ViewModelProvider(this, factory).get(RoomStoryViewModel::class.java)

        add_story_btn.setOnClickListener {
            insertStoryIntoDatabase()
        }
    }

    private fun insertStoryIntoDatabase() {
        val userId = 1
        val title = story_title_et.editText?.text.toString()
        val body = story_body_et.editText?.text.toString()

        if (inputCheck(userId, title, body)) {
            //Create Post Object
            val post = Stories(0, userId, title, body)

            //Add Post to database
            viewModel.addStory(post)

            Log.d("TAG", "insertStoryIntoDatabase: $post")

            Toast.makeText(this, "Story added successfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, StoriesActivity::class.java))
        } else {
            story_title_et.error = "This field cannot be empty"
            story_body_et.error = "This field cannot be empty"
            Toast.makeText(this, "Please fill out the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(userId: Int, title: String, body: String): Boolean {
        return !(TextUtils.isEmpty(userId.toString()) || TextUtils.isEmpty(title) || TextUtils.isEmpty(
            body
        ))
    }
}