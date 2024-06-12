package com.example.flixapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flixapp.adapters.CategoryAdapter
import com.example.flixapp.adapters.MovieAdapter
import com.example.flixapp.databinding.ActivityMainBinding
import com.example.flixapp.model.Category
import com.example.flixapp.model.Movie
import com.example.flixapp.util.CategoryTask

class MainActivity : AppCompatActivity(), CategoryTask.Callback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        progress = binding.progrssBar

        val listaFilmes = mutableListOf<Movie>()
        val listaCateria = mutableListOf<Category>()
        /*for ( j in 0 .. 6) {
            for (i in 0 until 15) {
                val movie = Movie(R.drawable.placeholder)
                listaFilmes.add(movie)
            }
            listaCateria.add(Category("Categoria ${j}", listaFilmes))
        }*/

        val rcvCategory = binding.rcvCategories
        val adapter = CategoryAdapter( listaCateria)
        rcvCategory.layoutManager = LinearLayoutManager(this)
        rcvCategory.adapter = adapter


        CategoryTask(this).executor("https://api.tiagoaguiar.co/netflixapp/home?apiKey=9186ca4b-8aa8-40f4-808d-df5cc9d7850d")

    }

    override fun onPreExecute() {
        progress.visibility = View.VISIBLE
    }

    override fun onResult(categories: List<Category>) {
        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
    }
}