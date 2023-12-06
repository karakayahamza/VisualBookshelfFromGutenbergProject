/*class LoadBookData(private val context: Context, private val webView: WebView) : AsyncTask<String, Void, String>() {

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

                val content = stringBuilder.toString()

                // Save the content to a local file
                saveToFile(content, "book_content.html")

                content
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: IOException) {
            // If there's an error loading from the network, try to load from the local file
            loadFromFile("book_content.html")
        }
    }

    override fun onPostExecute(result: String?) {
        if (result != null) {
            val formattedText = formatText(result)
            val centeredHtml = "<html><head><style>" +
                    "body { margin: 0; padding: 0; text-align: center; }" +
                    "img { max-width: 100%; height: auto; }" +
                    "</style></head><body>$formattedText</body></html>"

            // Load the formatted and centered text into the WebView
            webView.loadDataWithBaseURL(null, centeredHtml, "text/html", "UTF-8", null)
        }
    }

    private fun formatText(text: String?): String {
        return text.orEmpty()
    }

    private fun saveToFile(content: String, fileName: String) {
        val file = File(context.filesDir, fileName)
        try {
            val fos = FileOutputStream(file)
            fos.write(content.toByteArray())
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadFromFile(fileName: String): String? {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            try {
                val `in` = file.inputStream()
                val reader = BufferedReader(InputStreamReader(`in`, "UTF-8"))
                val stringBuilder = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }

                stringBuilder.toString()
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }
}*/

import android.content.Context
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class LoadBookData(private val context: Context) : AsyncTask<String, Void, String?>() {

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
