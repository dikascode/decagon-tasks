package com.decagon.navcontrollerblog.recycler_interface

import com.decagon.navcontrollerblog.data.Stories

interface RecyclerViewClickInterface {
    fun onItemClicked(position: Int, post: Stories)

//    fun onLongItemClicked(position: Int)
}