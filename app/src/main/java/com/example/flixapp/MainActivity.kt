package com.example.flixapp

import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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



        val listaFilmes = mutableListOf<Movie>()
        val listaCateria = mutableListOf<Category>()
        for ( j in 0 .. 6) {
            for (i in 0 until 15) {
                val movie = Movie(R.drawable.placeholder)
                listaFilmes.add(movie)
            }
            listaCateria.add(Category("Categoria ${j}", listaFilmes))
        }

        val rcvCategory = binding.rcvCategories
        val adapter = CategoryAdapter( listaCateria)
        rcvCategory.layoutManager = LinearLayoutManager(this)
        rcvCategory.adapter = adapter


    }
}