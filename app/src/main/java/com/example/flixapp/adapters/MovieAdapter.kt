package com.example.flixapp.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.flixapp.R
import com.example.flixapp.databinding.ItemCelulaMovieBinding
import com.example.flixapp.model.Movie
import com.example.flixapp.util.DownloadImageTask

class MovieAdapter(
    private val listaMovies: MutableList<Movie>
): RecyclerView.Adapter<MovieAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.VH {
        val view = LayoutInflater.from(parent.context).inflate( R.layout.item_celula_movie, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.VH, position: Int) {
        val posicaoItem = listaMovies[position]

        holder.bind(posicaoItem)
    }

    override fun getItemCount(): Int {
        return listaMovies.size
    }

    inner class VH(itemView: View): RecyclerView.ViewHolder( itemView ){

        fun bind( item: Movie ){
            
            val img = itemView.findViewById<ImageView>(R.id.imgCelula)
            DownloadImageTask( object : DownloadImageTask.Callback{
                override fun onResult(bitmap: Bitmap) {
                    img.setImageBitmap(bitmap)
                }
            }).execute(item.coverUrl)

        }
    }
}