package de.tuchemnitz.iconfinder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import de.tuchemnitz.iconfinder.model.IconViewModel

/**
 * Activity for the study flow.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val sharedViewModel: IconViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // disable night mode for the app to avoid different results because of the background color
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onPause() {
        super.onPause()
        // when the home button is pressed (or the app is paused in another way)
        // delete data from the incomplete study and navigate back to the InstructionFragment
        sharedViewModel.clearData()
        this.findNavController(R.id.nav_host_fragment)
            .navigate(R.id.action_global_to_instruction_fragment)
    }
}

// To do stuff
// TODO: Add new fragment/warning when database save not successful?
// TODO: Rename icons
// TODO: Nicer layout

// Final stuff
// TODO: Set time for delay (2 seconds in BA)
// TODO: Redo app icon with final icon images