package com.example.visualbookshelffromgutenbergproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.visualbookshelffromgutenbergproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView)

        setupWithNavController(bottomNavigationView,navController)
        



    }


}