package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
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

    // creating a variable for firebase firestore
    private var db: FirebaseFirestore? = null

    // start time needed to calculate the total time the user needed to click an icon
    private var startTime: Long = 0

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
     * Therefore, a back button press navigates the user back to the InstructionFragment
     * and clear all data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // getting our instance from firebase firestore
        db = FirebaseFirestore.getInstance()

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            // pressing the back button deletes all progress
            override fun handleOnBackPressed() {
                // reset data
                sharedViewModel.clearData()
                // go back to InstructionFragment
                findNavController().navigate(R.id.action_global_to_instruction_fragment)
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
     * Shows all nine icons as clickable buttons in random order on the 3x3 grid.
     * If the study is in phase 1 or 3, colorful icons are shown,
     * if the study is in phase 2 or 4, black and white icons are shown.
     */
    private fun showRandomOrderIcons() {
        // all icon buttons in all_icons_fragment.xml
        val buttonArray = arrayOf(
            binding?.iconButtonZero,
            binding?.iconButtonOne,
            binding?.iconButtonTwo,
            binding?.iconButtonThree,
            binding?.iconButtonFour,
            binding?.iconButtonFive,
            binding?.iconButtonSix,
            binding?.iconButtonSeven,
            binding?.iconButtonEight
        )

        // set shuffle list to random order list of elements from icon list
        sharedViewModel.setShuffleList(
            sharedViewModel.getIcons().shuffled().toMutableList()
        )

        // bind icon images to buttons from array
        var i = 0
        if (sharedViewModel.getPhase() == 1 || sharedViewModel.getPhase() == 3) {
            for (button in buttonArray) {
                binding?.apply {
                    button?.setImageResource(sharedViewModel.getShuffleList()[i].colorIcon)
                }
                i++
            }
        } else {
            for (button in buttonArray) {
                binding?.apply {
                    button?.setImageResource(sharedViewModel.getShuffleList()[i].blackWhiteIcon)
                }
                i++
            }
        }
    }

    /**
     * Tests if the clicked icon is the same as the shown icon.
     * For now only prints the result to the LogCat.
     */
    fun onIconClicked(buttonNumber: Int) {
        // calculate the time until the user clicked the icon
        val elapsedTime = System.nanoTime() - startTime
        val timeInSeconds = elapsedTime / 1_000_000_000.0 // conversion from nanoseconds to seconds

        // check if user clicked the right icon (the one shown before)
        val correctIconClicked =
            sharedViewModel.getShuffleList()[buttonNumber].imgId == sharedViewModel.getShownIcon()

        // save data in a list
        addDataToList(timeInSeconds, correctIconClicked)

        // next fragment to be shown is either OneIconFragment or ResultFragment
        navigateToNextFragment()
    }

    /**
     * Save data in every iteration to one list.
     * For each element of the list the current phase, the id of the shown icon, the correctness
     * and needed time in seconds are saved.
     */
    private fun addDataToList(timeInSeconds: Double, correctIconClicked: Boolean) {
        val data = IconViewModel.StudyData(
            sharedViewModel.getPhase(),
            sharedViewModel.getShownIcon(),
            correctIconClicked,
            timeInSeconds
        )
        sharedViewModel.addData(data)
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
                    findNavController().navigate(R.id.action_global_to_instruction_fragment)
                }
                2 -> {
                    sharedViewModel.setPhase(3)
                    findNavController().navigate(R.id.action_global_to_instruction_fragment)
                }
                3 -> {
                    sharedViewModel.setPhase(4)
                    findNavController().navigate(R.id.action_global_to_instruction_fragment)
                }
                4 -> {
                    // only when the last phase is completed and the study has not been done before
                    // the data is added
                    if (!sharedViewModel.getStudyAlreadyDone()) {
                        addDataToFirebase()
                        sharedViewModel.setStudyAlreadyDone()
                    }
                    findNavController().navigate(R.id.action_allIconsFragment_to_thankYouFragment)
                }
            }
        } else {
            findNavController().navigate(R.id.action_allIconsFragment_to_oneIconFragment)
        }
    }

    /**
     * Adds all data that was collected in the study to the FireStore Database.
     * Each added document contains the phase of the study, the id of the icon the user saw,
     * the correctness of the clicked icon and the needed time.
     * The function is only called after the last phase is finished, so no incomplete data is added.
     */
    private fun addDataToFirebase() {
        // creating a collection reference for our Firebase Firetore database.
        val dbIconFinder = db!!.collection("IconFinder")

        // adds each dataset of the type StudyData to the database
        for (dataSet in sharedViewModel.getData()) {
            dbIconFinder.add(dataSet)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}