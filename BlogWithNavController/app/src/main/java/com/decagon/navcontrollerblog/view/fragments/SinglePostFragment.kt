package com.decagon.navcontrollerblog.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.navcontrollerblog.R
import com.decagon.navcontrollerblog.data.BlogDatabase
import com.decagon.navcontrollerblog.data.Comments
import com.decagon.navcontrollerblog.data.Stories
import com.decagon.navcontrollerblog.network.RetroInstance
import com.decagon.navcontrollerblog.network.RetroService
import com.decagon.navcontrollerblog.repository.RoomRepositoryImpl
import com.decagon.navcontrollerblog.view.adapter.CommentsAdapter
import com.decagon.navcontrollerblog.viewmodel.RoomCommentViewModel
import com.decagon.navcontrollerblog.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.card_layout_story.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_single_post.*
import kotlinx.android.synthetic.main.fragment_single_post.view.*

class SinglePostFragment : Fragment() {
    private lateinit var commentViewModel: RoomCommentViewModel
    private lateinit var adapter: CommentsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_single_post, container, false)

        //Instantiate recyclerview
        recyclerView = view.comments_rv

        //Obtain object from serializable
        val myPostDetails = this.arguments?.getSerializable("singlePost") as Stories

        /**
         *Set View components to respective data
         */
        view.tv_story_title.text = myPostDetails.title
        view.tv_story_body.text = myPostDetails.body

        //Instance of all DAOs
        val storyDao = BlogDatabase.getDatabase(view.context).storyDao()
        val commentDao = BlogDatabase.getDatabase(view.context).commentDao()

        //Retrofit instance of Service
        val retroInstance: RetroService =
            RetroInstance.getRetroInstance().create(RetroService::class.java)

        /**
         *Repository and ViewModelFactory instance
         */
        val repository = RoomRepositoryImpl(storyDao, commentDao, retroInstance)
        val viewModelFactory = ViewModelFactory(repository)

        commentViewModel =
            ViewModelProvider(this, viewModelFactory).get(RoomCommentViewModel::class.java)

        /**
         * Show unique comments for a story
         */
        showComments(myPostDetails.id)


        /**
         * FloatButton onclick implementation
         */
        view.comment_fab.setOnClickListener {
            /**
             * Transfer serialized Post object
             */
            val bundle = bundleOf(
                "singlePostDetails" to myPostDetails
            )

            //Navigate to add comment fragment
            findNavController().navigate(
                R.id.action_singlePostFragment_to_addCommentFragment,
                bundle
            )
        }

//        Log.d("TAG", "onCreateView: $myPostDetails")

        return view
    }


    //Comments observer implementation
    private fun showComments(id: Int) {
        //Fetch comments for post n save to room
        commentViewModel.fetchCommentsFromEndpoint(id)

        //Observer
        val commentObserver: LiveData<List<Comments>> = commentViewModel.getCommentFromRoom(id)
        commentObserver.observe(viewLifecycleOwner, Observer {
            adapter.setCommentsList(it)
        })

        //Adapter instance
        adapter = context?.let { CommentsAdapter(it) }!!

        /**
         *Recyclerview
         */
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(view?.context)
        }
    }


}