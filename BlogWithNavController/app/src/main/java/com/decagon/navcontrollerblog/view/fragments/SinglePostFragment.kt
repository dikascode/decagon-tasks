package com.decagon.navcontrollerblog.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decagon.navcontrollerblog.R
import com.decagon.navcontrollerblog.data.Stories
import kotlinx.android.synthetic.main.card_layout_story.view.*

class SinglePostFragment : Fragment() {
    private val bundle = this.arguments

    val singlePostData =  bundle?.getSerializable("POST")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_single_post, container, false)

        val myPostDetails = this.arguments?.getSerializable("singlePost") as Stories

        view.tv_story_title.text = myPostDetails.title
        view.tv_story_body.text = myPostDetails.body

        Log.d("TAG", "onCreateView: $myPostDetails")
        return view
    }


}