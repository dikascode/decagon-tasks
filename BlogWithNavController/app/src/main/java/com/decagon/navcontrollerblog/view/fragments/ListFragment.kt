package com.decagon.navcontrollerblog.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.navcontrollerblog.R
import com.decagon.navcontrollerblog.data.BlogDatabase
import com.decagon.navcontrollerblog.data.Stories
import com.decagon.navcontrollerblog.network.RetroInstance
import com.decagon.navcontrollerblog.network.RetroService
import com.decagon.navcontrollerblog.recycler_interface.RecyclerViewClickInterface
import com.decagon.navcontrollerblog.repository.RoomRepositoryImpl
import com.decagon.navcontrollerblog.view.adapter.PostsListAdapter
import com.decagon.navcontrollerblog.viewmodel.RoomStoryViewModel
import com.decagon.navcontrollerblog.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment(), RecyclerViewClickInterface {
    private lateinit var storyViewModel: RoomStoryViewModel
    private lateinit var adapter: PostsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //Instance of all Daos
        val storyDao = BlogDatabase.getDatabase(view.context).storyDao()
        val commentDao = BlogDatabase.getDatabase(view.context).commentDao()

        //Recyclerview
        adapter = PostsListAdapter(view.context)
        val recyclerView = view.rv_stories
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        //Retrofit instance of Service
        val retroInstance: RetroService =
            RetroInstance.getRetroInstance().create(RetroService::class.java)

        /**
         * Repository and ViewModelFactory instance
         */
        val repository = RoomRepositoryImpl(storyDao, commentDao, retroInstance)
        val viewModelFactory = ViewModelFactory(repository)


        storyViewModel =
            ViewModelProvider(this, viewModelFactory).get(RoomStoryViewModel::class.java)


        /**
         * Stories list observer
         */
        storyViewModel.readAllStories.observe(viewLifecycleOwner, Observer { story ->
            adapter.setRoomStoryList(story as ArrayList<Stories>, this)
        })

        /**
         * FloatButton onclick implementation
         */
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //make the options appear in Toolbar
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    /**
     * Search implementation in options menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

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
                    val searchResult = storyViewModel.getSearchPosts(searchString)

                    searchResult.observe(viewLifecycleOwner, Observer {
                        adapter.setRoomStoryList(it as ArrayList<Stories>, this@ListFragment)
                    })

                }

                return false
            }

        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClicked(position: Int) {
        findNavController().navigate(R.id.action_listFragment_to_singlePostFragment)
    }

}