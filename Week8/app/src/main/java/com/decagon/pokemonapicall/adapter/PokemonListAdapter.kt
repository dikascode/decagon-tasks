package com.decagon.pokemonapicall.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagon.pokemonapicall.R
import com.decagon.pokemonapicall.ui.SinglePokemonActivity
import com.decagon.pokemonapicall.model.Result

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


        holder.card_view.setOnClickListener { it ->

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

/**
 * Obtain pokemon unique ID from URL
 */
fun getPokemonId(url: String): String{
        val urlSplit = url.split("/")
        return urlSplit[urlSplit.lastIndex-1]
    }

