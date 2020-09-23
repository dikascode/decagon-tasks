package com.decagon.mvcstories.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvcstories.R
import com.decagon.mvcstories.controller.PostController
import com.decagon.mvcstories.data.Post
import kotlinx.android.synthetic.main.activity_add_story.*

class AddStory : AppCompatActivity() {
    private lateinit var postController: PostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_story)

        //post controller Instance
       postController = PostController(this)


        /**
         * Insert Post implementation
         */
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
            val post = Post(0, userId, title, body)

            //Add Post to database
            postController.addStory(post)

//            Log.d("TAG", "insertStoryIntoDatabase: $post")

            Toast.makeText(this, "Story added successfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, PostsActivity::class.java))
        } else {
            story_title_et.error = "This field cannot be empty"
            story_body_et.error = "This field cannot be empty"
            Toast.makeText(this, "Please fill out the fields", Toast.LENGTH_SHORT).show()
        }
    }

    //Edit text Input Check
    private fun inputCheck(userId: Int, title: String, body: String): Boolean {
        return !(TextUtils.isEmpty(userId.toString()) || TextUtils.isEmpty(title) || TextUtils.isEmpty(
            body
        ))
    }
    }
