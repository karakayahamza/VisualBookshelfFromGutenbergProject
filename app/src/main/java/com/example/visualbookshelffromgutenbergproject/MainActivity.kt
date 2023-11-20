package com.example.visualbookshelffromgutenbergproject

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.visualbookshelffromgutenbergproject.databinding.ActivityMainBinding
import com.example.visualbookshelffromgutenbergproject.fragments.FavoriteBooks
import com.example.visualbookshelffromgutenbergproject.fragments.SearchBook

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

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

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://www.gutenberg.org/cache/epub/84/pg84.txt")
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

                    withContext(Dispatchers.Main) {
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
                    }
                } else {
                    println("HTTP Request Failed. Response Code: $responseCode")
                }

                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/


 /*       val firstFragment=SearchBook()
        //val secondFragment=BookInfoDialogFragment()
        val thirdFragment = FavoriteBooks()

        binding.navView.selectedItemId = R.id.search

        setCurrentFragment(firstFragment)


        binding.navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.search->setCurrentFragment(firstFragment)
                //R.id.settings->setCurrentFragment(secondFragment)
                R.id.favorites -> setCurrentFragment(thirdFragment)

            }
            true
        }
*/

    }



   /* private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }*/
    /*
    private fun createPageLayout(pageNumber: Int, pageContent: String): LinearLayout {
        val pageLayout = LinearLayout(this)
        pageLayout.orientation = LinearLayout.VERTICAL

        // Sayfa içeriğini gösteren TextView
        val pageTextView = TextView(this)
        pageTextView.text = pageContent
        pageTextView.textSize = 18f
        pageTextView.setTextColor(resources.getColor(R.color.black))
        pageTextView.setPadding(16, 8, 16, 8)
        pageLayout.addView(pageTextView)

        return pageLayout
    }

    private fun saveLastReadScrollY(scrollY: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("lastReadScrollY", scrollY)
        editor.apply()
    }

    private fun getLastReadScrollY(): Int {
        return sharedPreferences.getInt("lastReadScrollY", 0)
    }*/




}