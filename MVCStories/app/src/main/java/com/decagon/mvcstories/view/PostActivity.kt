package com.decagon.mvcstories.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvcstories.R
import com.decagon.mvcstories.adapter.CommentsAdapter
import com.decagon.mvcstories.controller.CommentController
import com.decagon.mvcstories.controller.PostController
import com.decagon.mvcstories.data.Comments
import com.decagon.mvcstories.data.Post
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.card_layout_story.*

class PostActivity : AppCompatActivity() {
    private lateinit var postController: PostController
    private lateinit var commentController: CommentController

    private lateinit var adapter: CommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        commentController = CommentController(this)
        postController = PostController(this)

        val id = intent.getIntExtra("STORY_ID", 1)


        showStory(id)

        showComments(id)

        /**
         * Assign view values and update Post model View
         */

        insertCommentIntoRoom(id)
    }

    private fun insertCommentIntoRoom(id: Int) {
        add_comment_btn.setOnClickListener {
            val username = "user2"
            val email = "user2@gmail.com"
            val comment = comment_et.text.toString()

            if(comment.isEmpty()){
                Toast.makeText(this, "Please write a comment", Toast.LENGTH_SHORT).show()
                comment_et.error = "Please write a comment"
            } else {
                val post = Comments(0, id, username, email, comment)

                commentController.addComment(post)

                //Refresh activity
                finish()
                startActivity(intent)

                Toast.makeText(this, "Comment added successfully", Toast.LENGTH_SHORT).show()
            }



        }
    }

    //Comments observer implementation
    private fun showComments(id: Int) {
        commentController.displayComments(id)

        val commentObserver: LiveData<List<Comments>> = commentController.getCommentFromRoom(id)
        commentObserver.observe(this, Observer {
            adapter.setCommentsList(it)
        })

        adapter = CommentsAdapter(this)
        comments_rv.adapter = adapter
    }

    //Story observer implementation
    private fun showStory(id: Int) {
        val singleStory: LiveData<Post> = postController.readStory(id)

        singleStory.observe(this, Observer {

            tv_story_title.text = it.title
            tv_story_body.text = it.body
        })
    }
}