package com.example.flixapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.example.flixapp.model.Movie
import com.example.flixapp.model.MovieDetails
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class MovieTask(private val callback: Callback) {

    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    interface Callback {
        fun onPreExecute()
        fun onResult( movieDerails: MovieDetails)
        fun onFailure(message: String)
    }

    fun execute( url: String) {

        handler.post {
            callback.onPreExecute()
        }

        executor.execute {

             //conexão
            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null

            try {
                val requestUrl = URL(url)
                urlConnection = requestUrl.openConnection() as HttpsURLConnection //abrindo conexão com a web
                urlConnection.readTimeout = 2000 // tempo de leitura da conexão (2s)
                urlConnection.connectTimeout = 2000 //Tempo de conexão

                val statusCode: Int = urlConnection.responseCode
                if ( statusCode == 400) {
                    stream = urlConnection.errorStream
                    val jsonAsString = stream.bufferedReader().use { it.readText() }
                    val json = JSONObject(jsonAsString)
                    val message = json.getString("message")
                    throw IOException(message)



                } else if (statusCode > 400) throw IOException("Erro de comunicação com o servidor")



                stream = urlConnection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() }
                val movieDetails = toMovieDetails(jsonAsString)

                handler.post {
                    callback.onResult(movieDetails)
                }


            } catch ( e: IOException) {
                //Log.e("Teste", e.message ?: "Erro desconhecido", e)
                val message = e.message ?: "erro desconhecido"
                handler.post {
                    callback.onFailure(message)
                }
            }
            finally {
                urlConnection.let { it?.disconnect() }
                stream?.close()
            }

        }

    }

    private fun toMovieDetails(jsonAsString: String): MovieDetails {

        val jsonRoot = JSONObject(jsonAsString)

        val id: Int = jsonRoot.getInt("id")
        val title: String = jsonRoot.getString("title")
        val desc = jsonRoot.getString("desc")
        val cast = jsonRoot.getString("cast")
        val moviesJson = jsonRoot.getJSONArray("movie")

        val similars = mutableListOf<Movie>()
        for (i in 0 until moviesJson.length()) {
            val movieJson = moviesJson.getJSONObject(i)

            val similarId = movieJson.getInt("id")
            val similarCoverUrl = movieJson.getString("cover_url")

            val mv = Movie(similarId, similarCoverUrl)
            similars.add(mv)
        }

        val movie = Movie(id, title, desc, cast)

        return MovieDetails(movie, similars)
    }
}