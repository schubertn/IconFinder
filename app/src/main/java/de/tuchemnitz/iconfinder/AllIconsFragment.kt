package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.AllIconsFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel

/**
 * This is the third screen of the IconFinder app.
 * The user sees a 3x3 grid with nine different icons and has to click on the one shown before,
 * the time needed to click the icon is saved.
 * Those icons can either be colorful or black and white, depending on the phase of the study.
 * Phases 1 and 3 show colorful icons, phases 2 and 4 black and white icons.
 * After clicking the icon, the user either sees the previous screen again with a different icon
 * or the next screen, depending on if all icons have already been shown.
 */
class AllIconsFragment : Fragment() {

    // Binding object instance corresponding to the all_icons_fragment.xml layout
    private var binding: AllIconsFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    // values needed for study with default values
    private var startTime: Long = 0
    private var timeInSeconds: Double = 0.0
    private var correctIconClicked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = AllIconsFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        showRandomOrderIcons()

        return fragmentBinding.root
    }

    /**
     * Handles back button press. If not handled, the user would see previous fragment again,
     * which would falsify the study data.
     * Therefore, a back button press navigates the user back to the WelcomeFragment
     * and clear all data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            // pressing the back button deletes all progress
            override fun handleOnBackPressed() {
                // reset data
                sharedViewModel.clearData()
                // go back to WelcomeFragment
                findNavController().navigate(R.id.action_global_to_welcome_fragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // used to bind button click listeners in this class to xml
        binding?.allIconsFragment = this
        // used to calculate the time the user needs to click the icon
        startTime = System.nanoTime()
    }

    /**
     * Shows all nine icons in random order on the 3x3 grid.
     * If the study is in phase 1 or 3, colorful icons are shown,
     * if the study is in phase 2 or 4, black and white icons are shown.
     */
    private fun showRandomOrderIcons() {
        // set shuffle list to random order list of elements from icon lists
        if (sharedViewModel.getPhase() == 1 || sharedViewModel.getPhase() == 3) {
            sharedViewModel.setShuffleList(
                sharedViewModel.getColorIcons().shuffled().toMutableList()
            )
        } else {
            sharedViewModel.setShuffleList(
                sharedViewModel.getBlackWhiteIcons().shuffled().toMutableList()
            )
        }

        // alternative: create array containing all buttons and then loop over array ?
        binding?.apply {
            iconButtonZero.setImageResource(sharedViewModel.getShuffleList()[0].imgId)
            iconButtonOne.setImageResource(sharedViewModel.getShuffleList()[1].imgId)
            iconButtonTwo.setImageResource(sharedViewModel.getShuffleList()[2].imgId)
            iconButtonThree.setImageResource(sharedViewModel.getShuffleList()[3].imgId)
            iconButtonFour.setImageResource(sharedViewModel.getShuffleList()[4].imgId)
            iconButtonFive.setImageResource(sharedViewModel.getShuffleList()[5].imgId)
            iconButtonSix.setImageResource(sharedViewModel.getShuffleList()[6].imgId)
            iconButtonSeven.setImageResource(sharedViewModel.getShuffleList()[7].imgId)
            iconButtonEight.setImageResource(sharedViewModel.getShuffleList()[8].imgId)
        }
    }

    /**
     * Tests if the clicked icon is the same as the shown icon.
     * For now only prints the result to the LogCat.
     */
    fun onIconClicked(buttonNumber: Int) {
        // calculate the time until the user clicked the icon
        val elapsedTime = System.nanoTime() - startTime
        timeInSeconds = elapsedTime / 1_000_000_000.0 // conversion from nanoseconds to seconds
        Log.d("TESTING", "time in nanoseconds: $elapsedTime")
        Log.d("TESTING", "time in seconds: $timeInSeconds")

        // check if user clicked the right icon (the one shown before)
        correctIconClicked =
            sharedViewModel.getShuffleList()[buttonNumber] == sharedViewModel.getShownIcon()

        // save data in a list
        saveData()
        // next fragment to be shown is either OneIconFragment or ResultFragment
        navigateToNextFragment()
    }


    /**
     * Navigation to the next fragment.
     * If the user has already seen all nine icons, the next phase is started by resetting the
     * list of shown icons and showing instructions for this phase.
     * If all four phases are done, the results will be shown.
     * If the user has not seen all nine icons, the next icon is shown.
     */
    private fun navigateToNextFragment() {
        if (sharedViewModel.getShownIcons().size > 8) {
            sharedViewModel.clearShownIcons()
            when (sharedViewModel.getPhase()) {
                1 -> {
                    sharedViewModel.setPhase(2)
                    // add individual navigation to instruction fragments here
                    findNavController().navigate(R.id.action_allIconsFragment_to_oneIconFragment)
                }
                2 -> {
                    sharedViewModel.setPhase(3)
                    findNavController().navigate(R.id.action_allIconsFragment_to_oneIconFragment)
                }
                3 -> {
                    sharedViewModel.setPhase(4)
                    findNavController().navigate(R.id.action_allIconsFragment_to_oneIconFragment)
                }
                4 -> findNavController().navigate(R.id.action_allIconsFragment_to_thankYouFragment)
            }
        } else {
            findNavController().navigate(R.id.action_allIconsFragment_to_oneIconFragment)
        }
    }

    /**
     * Save data in every iteration to one list.
     * The list contains objects with the type StudyData.
     * In there the shown icon, the correctness and needed time are saved.
     */
    private fun saveData() {
        val data = IconViewModel.StudyData(
            sharedViewModel.getShownIcon(),
            correctIconClicked,
            timeInSeconds
        )
        sharedViewModel.addData(data)
        printMyData()
    }

    /**
     * Temporary function to print the data for testing purposes.
     */
    private fun printMyData() {
        for (data in sharedViewModel.getData()) {
            println("Icon shown: " + (context?.resources?.getResourceName(data.shownIcon.imgId)))
            println("Icon was correct: " + data.correctness)
            println("Time needed: " + data.timeNeeded + " seconds")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}