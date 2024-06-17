package com.example.flixapp.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.flixapp.R
import com.example.flixapp.databinding.ItemCelulaMovieBinding
import com.example.flixapp.model.Movie
import com.example.flixapp.util.DownloadImageTask

class MovieAdapter(
    private val listaMovies: MutableList<Movie>,
    @LayoutRes private val layoutId: Int,
    private val onItemClickListener: ( (Int) -> Unit )? = null
): RecyclerView.Adapter<MovieAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.VH {
        val view = LayoutInflater.from(parent.context).inflate( layoutId, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.VH, position: Int) {
        val posicaoItem = listaMovies[position]

        holder.bind(posicaoItem)
    }

    override fun getItemCount(): Int = listaMovies.size

    inner class VH(itemView: View): RecyclerView.ViewHolder( itemView ){

        fun bind( item: Movie ){
            
//            val img = itemView.findViewById<ImageView>(R.id.imgCelula)
            val imgCover: ImageView = itemView.findViewById(R.id.imgCeluladet)
            imgCover.setOnClickListener {
                onItemClickListener?.invoke(item.id)
            }



            DownloadImageTask( object : DownloadImageTask.Callback{
                override fun onResult(bitmap: Bitmap) {
                    imgCover.setImageBitmap(bitmap)
                }
            }).execute(item.coverUrl)

        }
    }
}