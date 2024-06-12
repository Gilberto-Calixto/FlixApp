package com.example.flixapp.util

import android.util.Log
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
            var buffer:BufferedInputStream? = null

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
                Log.e("Teste", jsonAsString)



            } catch (e: IOException) {
                Log.e("Teste", e.message ?: "Erro desconhecido", e)
            }
        }
    }

}