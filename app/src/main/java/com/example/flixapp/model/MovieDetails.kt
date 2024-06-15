package com.example.flixapp.model

data class MovieDetails(
    val movie: Movie,
    val similars: List<Movie>
)
