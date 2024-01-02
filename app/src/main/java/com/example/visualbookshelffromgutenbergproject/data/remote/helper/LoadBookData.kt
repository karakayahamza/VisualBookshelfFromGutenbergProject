package com.example.visualbookshelffromgutenbergproject.data.remote.helper

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class LoadBookData : AsyncTask<String, Void, String?>() {

    override fun doInBackground(vararg params: String): String? {
        val urlString = params[0]

        return try {
            val url = URL(urlString)
            val urlConnection = url.openConnection() as HttpURLConnection

            try {
                val `in`: InputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(`in`, "UTF-8"))
                val stringBuilder = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }

                stringBuilder.toString()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: IOException) {
            null
        }
    }
}
