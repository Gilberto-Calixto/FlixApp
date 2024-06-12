package com.example.flixapp.util

import android.util.Log
import com.example.flixapp.model.Category
import com.example.flixapp.model.Movie
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.util.concurrent.Executors

class CategoryTask {

    fun executor (url: String) {

        val executor = Executors.newSingleThreadExecutor()

        executor.execute {

            var urlConnection: HttpURLConnection? = null
            var stream: InputStream? = null

            try {

                val requestUrl = URL(url) //abrir URL
                urlConnection =  requestUrl.openConnection() as HttpURLConnection //abrir conexão
                urlConnection.readTimeout = 2000 // tempo leitura(2s)
                urlConnection.connectTimeout = 200 // tempo de conexão(2s)

                //verificar status code
                val stateCode: Int = urlConnection.responseCode
                if (stateCode > 400)  throw IOException("Erro de cominicação com o servido")

                //buscar bytes
                stream = urlConnection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() } // bytes em String
                //Log.e("Teste", jsonAsString)
                //#Transformando em data class
                toCategory(jsonAsString)



            } catch (e: IOException) {
                Log.e("Teste", e.message ?: "Erro desconhecido", e)
            }
        }
    }

    private fun toCategory(jsonAsString: String): List<Category> {

        val listaCagory = mutableListOf<Category>()

        val jSonRoot = JSONObject(jsonAsString) //Raiz do json
        val jsonCategory = jSonRoot.getJSONArray("categpry") // lista de coleção dos elementos
        for (i in 0 until jsonCategory.length()) { // indo de 0 até o tamanho maximo da coleção
            val jsonCategories = jsonCategory.getJSONObject(i) //raiz dos objetos percorridos na coleção
            val title = jsonCategories.getString("title")
            val jsonMovies = jsonCategories.getJSONArray("movies")

            val movies = mutableListOf<Movie>()
            for ( j in 0 until jsonMovies.length()) {
                val jsonMovie = jsonMovies.getJSONObject(j)
                val id = jsonMovie.getInt("id")
                val coverUrl = jsonMovie.getString("cover_url")

                movies.add(Movie(id, coverUrl))
            }
            listaCagory.add(Category(title, movies))
        }

        return listaCagory
    }

}