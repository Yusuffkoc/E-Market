package com.example.e_market

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.e_market.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setBottomNavController()

    }

    private fun setBottomNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.productsNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    Navigation.findNavController(this, R.id.productsNavHostFragment)
                        .navigate(R.id.homeFragment)
                    true
                }
                R.id.shoppingBoxFragment -> {
                    Navigation.findNavController(this, R.id.productsNavHostFragment)
                        .navigate(R.id.shoppingBoxFragment)
                    true
                }
                R.id.favouritesFragment -> {

                    Navigation.findNavController(this, R.id.productsNavHostFragment)
                        .navigate(R.id.favouritesFragment)

                    true
                }
                else -> false

            }

        }
    }

    fun setMenuItem(item:Int){
        binding.bottomNavigationView.menu.get(item).isChecked = true
    }

}