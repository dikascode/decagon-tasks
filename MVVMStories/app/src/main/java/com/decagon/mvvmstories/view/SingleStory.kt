package com.decagon.mvvmstories.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvvmstories.R
import com.decagon.mvvmstories.adapter.CommentsAdapter
import com.decagon.mvvmstories.data.Comments
import com.decagon.mvvmstories.data.Stories
import com.decagon.mvvmstories.viewModel.RoomCommentViewModel
import com.decagon.mvvmstories.viewModel.RoomStoryViewModel
import kotlinx.android.synthetic.main.activity_single_story.*
import kotlinx.android.synthetic.main.card_layout_story.*

class SingleStory : AppCompatActivity() {
    private lateinit var commentViewModel: RoomCommentViewModel
    private lateinit var storyViewModel: RoomStoryViewModel

    private lateinit var adapter: CommentsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_story)

        commentViewModel = ViewModelProvider(this).get(RoomCommentViewModel::class.java)
        storyViewModel = ViewModelProvider(this).get(RoomStoryViewModel::class.java)

        /**
         * Get post id from intent
         */
        val id = intent.getIntExtra("STORY_ID", 1)

        /**
         * Show unique story
         */
        showStory(id)

        /**
         * Show unique comments for a story
         */
        showComments(id)

        /**
         * Assign view values and update Post model View
         */

        insertCommentIntoRoom(id)
    }

    /**
     * Insert comment into room implementation
     */
    private fun insertCommentIntoRoom(id: Int) {
        add_comment_btn.setOnClickListener {
            val username = "user1"
            val email = "dika@gmail.com"
            val comment = comment_et.text.toString()

            if(comment.isEmpty()){
                Toast.makeText(this, "Please write a comment", Toast.LENGTH_SHORT).show()
                comment_et.error = "Please write a comment"
            } else {
                val post = Comments(0, id, username, email, comment)

                commentViewModel.addComment(post)

                //Refresh activity
                finish()
                startActivity(intent)

                Toast.makeText(this, "Comment added successfully", Toast.LENGTH_SHORT).show()

//            Toast.makeText(this, "$id, $username, $email, $comment", Toast.LENGTH_LONG).show()
            }



        }
    }

    //Comments observer implementation
    private fun showComments(id: Int) {
        commentViewModel.displayComments(id)

        val commentObserver: LiveData<List<Comments>> = commentViewModel.getCommentFromRoom(id)
        commentObserver.observe(this, Observer {
            adapter.setCommentsList(it)
        })

        adapter = CommentsAdapter(this)
        comments_rv.adapter = adapter
    }

    //Story observer implementation
    private fun showStory(id: Int) {
        val singleStory: LiveData<Stories> = storyViewModel.readStory(id)

        singleStory.observe(this, Observer {

            tv_story_title.text = it.title
            tv_story_body.text = it.body
        })
    }


}