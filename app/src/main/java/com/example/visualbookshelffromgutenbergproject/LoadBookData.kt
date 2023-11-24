package com.example.visualbookshelffromgutenbergproject

import android.os.AsyncTask
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class LoadBookData(private val textView: TextView) : AsyncTask<String, Void, String>() {

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
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: String?) {
        val formattedText = formatText(result)
        textView.text = formattedText

        //println("formattedText: $formattedText  Result : $result")
    }

    private fun formatText(text: String?): String {
        // Burada metni istediğiniz gibi biçimlendirebilirsiniz.
        // Örneğin, "\n" karakterleri ile bölerek yeni satırlar ekleyebilirsiniz.
        // Bu kısımda metni nasıl biçimlendirmek istediğinize göre uygun işlemleri gerçekleştirin.
        return text?.replace("\n\n", "\n").orEmpty()
    }
}

