package com.example.flixapp

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flixapp.adapters.MovieAdapter
import com.example.flixapp.databinding.ActivityMovieBinding
import com.example.flixapp.model.Movie
import com.example.flixapp.model.MovieDetails
import com.example.flixapp.util.DownloadImageTask
import com.example.flixapp.util.MovieTask

class MovieActivity : AppCompatActivity(), MovieTask.Callback {

    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityMovieBinding
    private lateinit var rcvDetails: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var adapter: MovieAdapter

    private lateinit var title: TextView
    private lateinit var desc: TextView
    private lateinit var cast: TextView

    val listaSemelhantes = mutableListOf<Movie>()

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


        title = binding.title
        desc = binding.desc
        cast = binding.sobre

        rcvDetails = binding.rcvMoviesDetails
        progress = binding.progressMD

        val id = intent?.getIntExtra("id", 0) ?: throw IllegalStateException("Id não encontrado")
        val url = "https:/api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=9186ca4b-8aa8-40f4-808d-df5cc9d7850d"
        MovieTask(this).execute(url)

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null



        rcvDetails.layoutManager = GridLayoutManager(this, 3)
        adapter = MovieAdapter( listaSemelhantes, R.layout.item_celula_movie_details)
        rcvDetails.adapter = adapter


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPreExecute() {
        progress.visibility = View.VISIBLE
    }

    override fun onResult(movieDerails: MovieDetails) {
        progress.visibility = View.GONE

        title.text = movieDerails.movie.title
        desc.text = movieDerails.movie.desc
        cast.text = getString(R.string.cast, movieDerails.movie.cast)

        listaSemelhantes.clear()
        listaSemelhantes.addAll(movieDerails.similars)
        adapter.notifyDataSetChanged()

        DownloadImageTask(object: DownloadImageTask.Callback{
            override fun onResult(bitmap: Bitmap) {

                val layerDraw: LayerDrawable = ContextCompat.getDrawable(this@MovieActivity, R.drawable.shadows) as LayerDrawable
                val imgCover = BitmapDrawable(resources, bitmap)
                layerDraw.setDrawableByLayerId(R.id.cover_drawable, imgCover)
                val coverImg = binding.moviePlay
                coverImg.setImageDrawable(layerDraw)

            }

        }).execute(movieDerails.movie.coverUrl)
        //Log.i("Teste", movieDerails.toString())
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}