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

class AllIconsFragment : Fragment() {

    private var binding: AllIconsFragmentBinding? = null
    private val sharedViewModel: IconViewModel by activityViewModels()
    private var startTime: Long = 0 // used to calculate the time the user needs to click the icon
    private var timeInSeconds: Double = 0.0
    private var correctIcon: Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = AllIconsFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // show all icons in random order
        showRandomOrderIcons()

        return fragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            // pressing the back button deletes all progress
            override fun handleOnBackPressed() {
                // reset list of shown icons
                sharedViewModel.clearShownIconsList()
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
     * Shows the nine icons in random order on the 3x3 grid.
     */
    private fun showRandomOrderIcons() {
        // set shuffle list to random order list of elements from all icon list
        sharedViewModel.setShuffleList(sharedViewModel.getAllIcons().shuffled().toMutableList())

        //  alternative: create array containing all buttons and then loop over array ?
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
        timeInSeconds = elapsedTime / 1_000_000_000.0
        Log.d("TESTING", "time in nanoseconds: $elapsedTime")
        Log.d("TESTING", "time in seconds: $timeInSeconds")

        // check if user clicked the right icon
        if (sharedViewModel.getShuffleList()[buttonNumber] == sharedViewModel.getShownIcon()) {
            Log.d("TESTING", "correct icon")
            correctIcon = true
        } else {
            Log.d("TESTING", "wrong icon")
            correctIcon = false
        }

        // save data in a list
        saveData()

        // go back to previous fragment
        showOneIcon()
    }

    /**
     * Navigation to previous fragment to show only one icon. The user will have to look at it
     * for a certain amount of time.
     */
    private fun showOneIcon() {
        findNavController().navigate(R.id.action_allIconsFragment_to_oneIconFragment)
    }

    /**
     * Save data in every iteration to one list.
     * The list contains objects with the type StudyData.
     * In there the shown icon, the correctness and needed time are saved.
     */
    private fun saveData() {
        val data = IconViewModel.StudyData(sharedViewModel.getShownIcon(), correctIcon, timeInSeconds)
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