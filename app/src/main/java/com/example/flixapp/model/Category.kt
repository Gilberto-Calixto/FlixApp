package com.example.flixapp.model

data class Category(
    val name: String,
    val listagemMovies: MutableList<Movie>
)
