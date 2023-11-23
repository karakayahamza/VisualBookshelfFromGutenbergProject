package com.example.visualbookshelffromgutenbergproject

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.visualbookshelffromgutenbergproject.databinding.ActivityMainBinding
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*binding.save.setOnClickListener {
            val currentScrollPosition = binding.scrollView.scrollY
            saveLastReadScrollY(currentScrollPosition)
        }
        //println(sharedPreferences.getInt("lastReadScrollY",0))

        sharedPreferences = getSharedPreferences("ReadingPreferences", Context.MODE_PRIVATE)
*/
/*
    GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://www.gutenberg.org/ebooks/84.txt.utf-8")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                        response.append("\n")
                    }

                    reader.close()


                    println("Response: " +response.toString())
                    /*withContext(Dispatchers.Main) {
                        val lastReadScrollY = getLastReadScrollY()
                        val data = response.toString()
                        val scrollViewContent: LinearLayout = findViewById(R.id.scrollViewContent)
                        val bookText = data

                        // Doğrudan bir sayfada gösterme, bütün metni göster
                        val pageLayout = createPageLayout(1, bookText)
                        scrollViewContent.addView(pageLayout)

                        // Kullanıcının kaldığı yeri kontrol et
                        binding.scrollViewContent.post {
                            binding.scrollView.scrollTo(0, lastReadScrollY)
                        }
                    }*/
                } else {
                    println("HTTP Request Failed. Response Code: $responseCode")
                }

                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

*/

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView)

        setupWithNavController(bottomNavigationView,navController)
}
}