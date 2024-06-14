package com.example.flixapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flixapp.R
import com.example.flixapp.databinding.ItemCategoryBinding
import com.example.flixapp.model.Category

class CategoryAdapter(
    private val listaCategory: MutableList<Category>
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryAdapter.CategoryViewHolder, position: Int
    ) {
        val itemPosition = listaCategory[position]

        holder.bin(itemPosition)
    }

    override fun getItemCount(): Int = listaCategory.size

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root){

        fun bin(category: Category) {

            //texto categoria
            val categoria = binding.textCategoria
            categoria.text = category.name

            //recyclerView
            val rcvFilmes = binding.rcvMovies
            rcvFilmes.layoutManager = LinearLayoutManager(
                binding.root.context, LinearLayoutManager.HORIZONTAL, false
            )
            val adapter = MovieAdapter(category.listagemMovies, R.layout.item_celula_movie)
            rcvFilmes.adapter = adapter



        }

    }
}