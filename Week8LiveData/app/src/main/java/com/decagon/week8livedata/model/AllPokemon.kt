package com.decagon.week8livedata.model


data class AllPokemon(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>
)

data class Result(
    val name: String = "Pikachu",
    val url: String = "www.google.com"
)
