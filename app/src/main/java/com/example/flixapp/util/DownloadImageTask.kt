package com.example.flixapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class DownloadImageTask(private val callback: Callback) {

    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    interface Callback {
        fun onResult(bitmap: Bitmap)
    }

    fun execute( url: String) {



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
                if (statusCode > 400) throw IOException("Erro de comunicação com o servidor")

                stream = urlConnection.inputStream

                val bitmap = BitmapFactory.decodeStream(stream)
                handler.post {
                    callback.onResult(bitmap)
                }

            } catch ( e: IOException) {
                //Log.e("Teste", e.message ?: "Erro desconhecido", e)
                val message = e.message ?: "erro desconhecido"
            }
            finally {
                urlConnection.let { it?.disconnect() }
                stream?.close()
            }

        }

    }
}