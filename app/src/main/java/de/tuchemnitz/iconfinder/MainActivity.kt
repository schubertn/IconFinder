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

    /**
     * Deletes the data from the incomplete study and navigates back to InstructionFragment
     * when home button on phone is pressed.
     */
    override fun onPause() {
        super.onPause()
        sharedViewModel.clearData()
        this.findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_to_instruction_fragment)
    }
}

// To do stuff
// TODO: StatisticsFragment with Firebase Data
// TODO: Design shown icons
// TODO: Rename icons and fragments
// TODO: Remove functions from onCreateView()?

// Additional stuff
// TODO: Nicer layout

// Final stuff
// TODO: Set time for delay (2 seconds in BA)
// TODO: Check for spelling errors
// TODO: Read through comments
// TODO: Rewrite texts
// TODO: Redo app icon with final icon images