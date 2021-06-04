package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.tuchemnitz.iconfinder.databinding.ResultFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel
import kotlin.math.round

// TODO
/**
 * This is one of the two last screens of the IconFinder app.
 * The user will see the study results in a table.
 * Each row contains all results concerning one icon. The first column contains images of the icons
 * and the other four columns the time needed to click the right icon in each phase.
 * Every number is either green or red to indicate whether the user clicked the right icon.
 */
class ResultFragment : Fragment() {

    // binding object instance corresponding to the result_fragment.xml layout
    private var binding: ResultFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = ResultFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        generateResultData()
        printMyData()

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply { iconViewModel = sharedViewModel }
    }

    /**
     * Convert data to a format suitable for result table.
     * Instead of Icon use Integer containing icon id to show the corresponding image.
     * Continue to use a Boolean value to indicate the correctness.
     * Instead of a Double use a String (rounded to three decimal places) for the time in seconds.
     * The resulting list is sorted by the icon ids and phases.
     */
    private fun generateResultData() {
        // convert data
        for (data in sharedViewModel.getData()) {
            sharedViewModel.addResultData(
                IconViewModel.ResultData(
                    data.iconId,
                    data.correctness,
                    data.timeNeeded.toCustomString()
                )
            )
        }
        // sort list by icon ids
        // icons with the same ids are sorted by phase since the sort is stable
        // (equal elements preserve their order relative to each other after sorting)
        sharedViewModel.getResultData().sortBy { sharedViewModel.selector(it) }

    }

    /**
     * Custom toString() function to return time with added "Sekunden"
     * and rounded to three decimal places.
     */
    private fun Double.toCustomString(): String {
        return "${round(this * 1000) / 1000} Sekunden"
    }

    // used for testing, will be removed later
    private fun printMyData() {
        for (data in sharedViewModel.getResultData()) {
            println("Icon shown: " + (sharedViewModel.getIcons()[data.iconId].imgId))
            println("Icon was correct: " + data.correct)
            println("Time needed: " + data.time)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}