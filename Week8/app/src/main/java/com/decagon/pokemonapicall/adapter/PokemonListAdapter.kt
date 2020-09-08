package com.decagon.pokemonapicall.adapter

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagon.pokemonapicall.MainActivity
import com.decagon.pokemonapicall.R
import com.decagon.pokemonapicall.SinglePokemonActivity
import com.decagon.pokemonapicall.SinglePokemonFragment
import com.decagon.pokemonapicall.`interface`.RetrofitService
import com.decagon.pokemonapicall.common.Common
import com.decagon.pokemonapicall.model.PokemonDetials
import com.decagon.pokemonapicall.model.Result
import kotlin.coroutines.coroutineContext

class PokemonListAdapter(
    internal var context: Context,
    internal var pokemonList: List<Result>
) : RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {
    lateinit var intent: Intent


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val id = getPokemonId(pokemonList[position].url)
        val imageUrl = "https://pokeres.bastionbot.org/images/pokemon/$id.png"
        Glide.with(context).load(imageUrl)
            .into(holder.img_pokemon)
        holder.text_pokemon.text = pokemonList[position].name
//        Log.i(TAG, "onBindViewHolder: ${holder.text_pokemon}")

        holder.card_view.setOnClickListener { it ->
//            Toast.makeText(it.context, "${pokemonList[position].name}", Toast.LENGTH_SHORT).show()
                intent = Intent(it.context, SinglePokemonActivity::class.java)
                intent.putExtra("IMAGE_URL", imageUrl)
                intent.putExtra("POKEMON_ID", id)
                it.context.startActivity(intent)
        }
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var img_pokemon: ImageView
        internal var text_pokemon: TextView
        internal var card_view: CardView

//        var thisContext = itemView.context

        init {
            img_pokemon = itemView.findViewById(R.id.pokemon_image) as ImageView
            text_pokemon = itemView.findViewById(R.id.pokemon_name) as TextView
            card_view = itemView.findViewById(R.id.card_view)

        }



    }
}


    fun getPokemonId(url: String): String{
        val urlSplit = url.split("/")
        return urlSplit[urlSplit.lastIndex-1]
    }

