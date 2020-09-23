package com.decagon.mvcstories.view

import android.content.Intent
import com.decagon.mvcstories.adapter.StoryAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.decagon.mvcstories.R
import com.decagon.mvcstories.controller.PostController
import com.decagon.mvcstories.data.Post
import kotlinx.android.synthetic.main.activity_posts.*

class PostsActivity : AppCompatActivity() {
    private lateinit var postController: PostController
    private lateinit var adapter: StoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        postController = PostController(this)
        postController.displayStories()

        /**
         * Add new Story FAB button
         */
        add_fab_button.setOnClickListener {
            val intent = Intent(this, AddStory::class.java)
            startActivity(intent)
        }


        /**
         * Stories list observer
         */
        postController.readAllStories.observe(this, Observer {
            adapter.setRoomStoryList(it as ArrayList<Post>)

        })

        adapter = StoryAdapter(this)
        rv_stories.adapter = adapter


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item: MenuItem = menu!!.findItem(R.id.action_search)
        val searchView: SearchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val searchString = "%$newText%"

                    //Observer for search
                    val searchResultObserver = postController.getSearchPosts(searchString)

                    searchResultObserver.observe(this@PostsActivity, Observer {
                        adapter.setRoomStoryList(it as ArrayList<Post>)
                    })

                }

//                adapter.filter.filter(newText)
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}