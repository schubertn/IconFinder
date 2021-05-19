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
        // disable night mode for this app to avoid differences because of the background color
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

// TODO: Rework ResultFragment (use database queries?)
// TODO: Design shown icons
// TODO: Rename icons and fragments

// Additional stuff
// TODO: Make everything uniform
// TODO: Order of icons in result table? (ordered by showing order - ordered by number - ordered by time)
// TODO: Could table in ResultFragment be created with RecyclerView, for-loop etc?
// TODO: Remove Databinding from OneIcon.xml?
// TODO: Make layout of ThankYouFragment nicer
// TODO: Remove function from onCreateView()?
// TODO: Save icons with their ids (Map?)

// Final stuff
// TODO: Set time for delay (2 seconds in BA)
// TODO: Check for spelling errors
// TODO: Read through comments
// TODO: Rewrite texts
// TODO: Redo app icon with final icon images