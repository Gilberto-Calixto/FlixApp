package com.example.flixapp.model

data class Category(
    val categoria: String,
    val listagemMovies: MutableList<Movie>
)
