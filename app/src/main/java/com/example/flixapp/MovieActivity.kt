package com.example.flixapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flixapp.adapters.MovieAdapter
import com.example.flixapp.databinding.ActivityMovieBinding
import com.example.flixapp.model.Movie
import com.example.flixapp.model.MovieDetails
import com.example.flixapp.util.MovieTask

class MovieActivity : AppCompatActivity(), MovieTask.Callback {

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

        val id = intent?.getIntExtra("id", 0) ?: throw IllegalStateException("Id não encontrado")
        val url = "https:/api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=9186ca4b-8aa8-40f4-808d-df5cc9d7850d"
        MovieTask(this).execute(url)

        title.text = "Batman"
        desc.text = "asdsds"
        sobre.text = "Ano:"



        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null



        rcvDetails.layoutManager = GridLayoutManager(this, 3)
        val adapter = MovieAdapter( listaSemelhantes, R.layout.item_celula_movie_details)
        rcvDetails.adapter = adapter


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPreExecute() {

    }

    override fun onResult(movieDerails: MovieDetails) {
        Log.i("Teste", movieDerails.toString())
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}