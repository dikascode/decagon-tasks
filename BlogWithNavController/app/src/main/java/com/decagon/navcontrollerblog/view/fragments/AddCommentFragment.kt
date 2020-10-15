package com.decagon.navcontrollerblog.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decagon.navcontrollerblog.R
import com.decagon.navcontrollerblog.data.BlogDatabase
import com.decagon.navcontrollerblog.data.Comments
import com.decagon.navcontrollerblog.data.Stories
import com.decagon.navcontrollerblog.network.RetroInstance
import com.decagon.navcontrollerblog.network.RetroService
import com.decagon.navcontrollerblog.repository.RoomRepositoryImpl
import com.decagon.navcontrollerblog.viewmodel.RoomCommentViewModel
import com.decagon.navcontrollerblog.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_add_comment.*

class AddCommentFragment : Fragment() {
    private lateinit var commentViewModel: RoomCommentViewModel
    private lateinit var addCommentBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_comment, container, false)

        //Obtain object from serializable
        val myPostDetails = this.arguments?.getSerializable("singlePostDetails") as Stories

        //Instantiate add comment button
        addCommentBtn = view.findViewById(R.id.comment_button)

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


        //Add comment impl
        insertCommentIntoRoom(myPostDetails.id, myPostDetails)

        return view
    }


    /**
     * Insert comment into room implementation
     */
    private fun insertCommentIntoRoom(id: Int, stories: Stories) {
        addCommentBtn.setOnClickListener {
            val username = "user3"
            val email = "dika@gmail.com"
            val comment = comment_et.editText?.text.toString()

            if (comment.isEmpty()) {
                Toast.makeText(context, "Please write a comment", Toast.LENGTH_SHORT).show()
                comment_et.error = "Please write a comment"
            } else {
                val post = Comments(0, id, username, email, comment)

                commentViewModel.addComment(post)

                /**
                 * Transfer serialized Post object
                 */
                val bundle = bundleOf(
                    "singlePost" to stories
                )

                //Navigate to SinglePost fragment
                findNavController().navigate(R.id.action_addCommentFragment_to_singlePostFragment, bundle)

                Toast.makeText(context, "Comment added successfully", Toast.LENGTH_SHORT).show()
            }


        }
    }

}