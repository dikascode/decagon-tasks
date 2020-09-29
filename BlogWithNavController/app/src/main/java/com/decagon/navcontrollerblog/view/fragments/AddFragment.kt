package com.decagon.navcontrollerblog.view.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decagon.navcontrollerblog.R
import com.decagon.navcontrollerblog.data.BlogDatabase
import com.decagon.navcontrollerblog.data.Stories
import com.decagon.navcontrollerblog.network.RetroInstance
import com.decagon.navcontrollerblog.network.RetroService
import com.decagon.navcontrollerblog.repository.RoomRepositoryImpl
import com.decagon.navcontrollerblog.viewmodel.RoomStoryViewModel
import com.decagon.navcontrollerblog.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_add.view.story_body_et
import kotlinx.android.synthetic.main.fragment_add.view.story_title_et

class AddFragment : Fragment() {
    private lateinit var storyViewModel: RoomStoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        val storyDao = BlogDatabase.getDatabase(view.context).storyDao()
        val commentDao = BlogDatabase.getDatabase(view.context).commentDao()

        //Retrofit instance of Service
        val retroInstance: RetroService =
            RetroInstance.getRetroInstance().create(RetroService::class.java)

        val repository = RoomRepositoryImpl(storyDao, commentDao, retroInstance)
        val viewModelFactory = ViewModelFactory(repository)
        storyViewModel = ViewModelProvider(this, viewModelFactory).get(RoomStoryViewModel::class.java)

        view.add_story_btn.setOnClickListener {
            /**
             * Insert Post into database implementation
             */
            insertStoryIntoDatabase()
        }

        return view
    }


    private fun insertStoryIntoDatabase() {
        val userId = 1
        val title = story_title_et.editText?.text.toString()
        val body = story_body_et.editText?.text.toString()

        if (inputCheck(userId, title, body)) {
            //Create Post Object
            val post = Stories(0, userId, title, body)

            //Add Post to database
            storyViewModel.addStory(post)

            Log.d("TAG", "insertStoryIntoDatabase: $post")

            Toast.makeText(context, "Story added successfully", Toast.LENGTH_SHORT).show()

            //Navigate back to Posts page
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            story_title_et.error = "This field cannot be empty"
            story_body_et.error = "This field cannot be empty"
            Toast.makeText(context, "Please fill out the fields", Toast.LENGTH_SHORT).show()
        }
    }

    //Edit text Input Check
    private fun inputCheck(userId: Int, title: String, body: String): Boolean {
        return !(TextUtils.isEmpty(userId.toString()) || TextUtils.isEmpty(title) || TextUtils.isEmpty(
            body
        ))
    }

}