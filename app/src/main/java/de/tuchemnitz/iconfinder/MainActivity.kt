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
     * Deletes the data from the incomplete study and navigates back to WelcomeFragment
     * when home button on phone is pressed.
     */
    override fun onPause() {
        super.onPause()
        sharedViewModel.clearData()
        this.findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_to_welcome_fragment)
    }
}

// DONE: Make return button on phone work properly (Disable/return to WelcomeFragment)
// DONE: Handle home button of phone (same as return button?)
// DONE: Continue 'Shared ViewModel Across Fragments' Tutorial
// DONE: Check if shownIcons in IconViewModel should be private/add functions
// DONE: Make nine icons appear randomly
// DONE: Check if clicked icon is shown icon
// DONE: Calculate time the user needed to click icon (list with shown icon - correct? - time)
// DONE: Save everything
// DONE: Fragment with results
// DONE: Use provisional icons (drafts of icons) to test everything
// DONE: Complete navigation
// DONE: Make layout of AllIconsFragment nicer (remove grey borders)
// DONE: Show actual results in ResultFragment
// TODO: Add black-white icons (layout of ResultFragment?)
// TODO: Clear data on back/home button press
// TODO: ThankYouFragment navigation back to start - do study again would falsify results
// TODO: Make user unable to do study again?
// DONE: Praktikumsbericht Stichpunkte
// DONE: Implement timer
// TODO: Design app icon (lupe als foreground, icons als background (bewegungseffekt))
// TODO: Design shown icons
// DONE: Make OneIconFragment nicer
// DONE: Set default (no) image in one_icon_fragment.xml

// Additional stuff
// TODO: Change colors for dark theme
// DONE: Goodbye Fragment with buttons (back to welcome - show results)
// TODO: Make everything uniform
// TODO: Order of icons in result table? (ordered by showing order - ordered by number - ordered by time)
// DONE: Should OnClickListener in WelcomeFragment be changed to use DataBinding?
// TODO: Could table in ResultFragment be created with RecyclerView, for-loop etc?
// DONE: Sort string.xml by usage
// TODO: Remove Databinding from OneIcon.xml?
// DONE: Better Fragment names (also change those in comments!)
// DONE: Styles for buttons
// TODO: Make layout of ThankYouFragment nicer
// TODO: Rewrite the texts
// TODO: Build warning: Please remove usages of jcenter()