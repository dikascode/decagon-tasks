package com.decagon.pokemonapicall.model


import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String
)