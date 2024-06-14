package com.example.flixapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flixapp.adapters.MovieAdapter
import com.example.flixapp.databinding.ActivityMovieBinding
import com.example.flixapp.model.Movie

class MovieActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityMovieBinding
    private lateinit var rcvDetails: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listaSemelhantes = mutableListOf<Movie>()
        for (i in 0 until 15) {
            val movie = Movie(i, "Carr$i")
            listaSemelhantes.add(movie)
        }

        val title = binding.title
        val desc = binding.desc
        val sobre = binding.sobre
        rcvDetails = binding.rcvMoviesDetails

        title.text = "Batman"
        desc.text = "asdsds"
        sobre.text = "Ano:"



        toolbar = binding.toolbar
        setSupportActionBar(toolbar)


        rcvDetails.layoutManager = GridLayoutManager(this, 3)
        val adapter = MovieAdapter( listaSemelhantes, R.layout.item_celula_movie_details)
        rcvDetails.adapter = adapter


    }
}