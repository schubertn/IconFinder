package de.tuchemnitz.iconfinder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import de.tuchemnitz.iconfinder.model.IconViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val sharedViewModel: IconViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // setupActionBarWithNavController(navController)
        // setContentView(R.layout.activity_main)
    }

    /**
     * Navigates back to WelcomeFragment when home button on phone is pressed and
     * resets list of shown icons.
     */
    override fun onPause() {
        super.onPause()
        sharedViewModel.clearShownIconsList()
        this.findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_to_welcome_fragment)
    }
}

// DONE: Make return button on phone work properly (Disable/return to WelcomeFragment)
// DONE: Handle home button of phone (same as return button?)
// DONE: Continue 'Shared ViewModel Across Fragments' Tutorial
// DONE: Check if shownIcons in IconViewModel should be private/add functions
// DONE: Lifecycleowner? (used with LiveData)
// DONE: Make nine icons appear randomly
// DONE: Check if clicked icon is shown icon
// DONE: Calculate time the user needed to click icon (list with shown icon - correct? - time)
// TODO: Save everything
// DONE: Fragment with results
// TODO: Use provisional icons (drafts of icons) to test everything
// DONE: Praktikumsbericht Stichpunkte
// DONE: Implement timer
// TODO: Change colors for dark theme
// TODO: Design app icon (lupe als foreground, icons als background (bewegungseffekt))
// TODO: Design shown icons (Sun beams as triangles, no color transitions?)
// DONE: Make OneIconFragment nicer
// DONE: Set default (no) image in one_icon_fragment.xml
