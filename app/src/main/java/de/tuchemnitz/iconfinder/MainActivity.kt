package de.tuchemnitz.iconfinder

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import de.tuchemnitz.iconfinder.model.IconViewModel

/**
 * Activity for the study flow.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val sharedViewModel: IconViewModel by viewModels()

    /**
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve NavController from the NavHostFragment
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // setupActionBarWithNavController(navController)
        // setContentView(R.layout.activity_main)
    }
    **/

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

// TODO: Add black-white icons (layout of ResultFragment?)
// (ThankYouFragment navigation back to start - do study again would falsify results) ->
// TODO: Make user unable to do study again?
// TODO: Design app icon (lupe als foreground, icons als background (bewegungseffekt))
// TODO: Design shown icons

// Additional stuff
// TODO: Change colors for dark theme
// TODO: Make everything uniform
// TODO: Order of icons in result table? (ordered by showing order - ordered by number - ordered by time)
// TODO: Could table in ResultFragment be created with RecyclerView, for-loop etc?
// TODO: Remove Databinding from OneIcon.xml?
// TODO: Make layout of ThankYouFragment nicer
// TODO: Rewrite the texts
// TODO: Build warning - Please remove usages of jcenter()