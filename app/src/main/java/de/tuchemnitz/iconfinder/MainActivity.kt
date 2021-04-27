package de.tuchemnitz.iconfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // setupActionBarWithNavController(navController)
        // setContentView(R.layout.activity_main)

        // DONE: Make return button on phone work properly (Disable/return to WelcomeFragment)
        // TODO: Handle home button of phone (same as return button?)
        // TODO: Continue 'Shared ViewModel Across Fragments' Tutorial
        // DONE: Implement timer
        // TODO: Change colors for dark theme
        // TODO: Design app icon (lupe als foreground, icons als background (bewegungseffekt))
        // DONE: Make OneIconFragment nicer
        // DONE: Set default (no) image in one_icon_fragment.xml


    }
}
