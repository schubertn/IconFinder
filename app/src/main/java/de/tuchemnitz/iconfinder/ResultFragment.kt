package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.ResultFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel

/**
 * This is one of the two last screens of the IconFinder app.
 * The user will see the study results in a table and a button.
 * Each row contains all results concerning one icon. The first column contains images of the icons
 * and the other four columns the time needed to click the right icon in each phase.
 * Every number is either green or red to indicate whether the user clicked the right icon.
 * The button below the table allows the user to navigate back to the previous screen.
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

        sortStudyData()

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply { iconViewModel = sharedViewModel }
        binding?.resultFragment = this
    }

    /**
     * Sort the data from the study in a list.
     * The data is of the type [IconViewModel.StudyData] and sorted by the ids of the icons.
     * Icons with the same ids are sorted by phase since the sort is stable, which means that
     * equal elements preserve their order relative to each other after sorting.
     */
    private fun sortStudyData() {
        sharedViewModel.setSortedData(
            sharedViewModel.getData().sortedBy { sharedViewModel.selector(it) })
    }

    /**
     * On button click navigate back to the previous screen, where the user can decide what to do
     * after reaching the end of the study.
     */
    fun backToPreviousScreen() {
        findNavController().navigate(R.id.action_resultFragment_to_thankYouFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}