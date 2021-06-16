package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.ThankYouFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel

/**
 * This is the fourth screen of the IconFinder app.
 * The user sees a thank you message and can click on one of three buttons.
 * The first one shows the results of the user, the second one statistics to compare the user
 * to other participants of the study and the last one brings the user back to the first screen.
 */
class ThankYouFragment : Fragment() {

    // binding object instance corresponding to the thank_you_fragment.xml layout
    private var binding: ThankYouFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = ThankYouFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.thankYouFragment = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // handle back button press correctly
        // the user will see the InstructionFragment again and not the previous fragment to
        // avoid falsifying the study data
        // previously collected data is deleted and the user can start the study again
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // reset data
                sharedViewModel.clearData()
                // go back to InstructionFragment
                findNavController().navigate(R.id.action_global_to_instruction_fragment)
            }
        })
    }

    /**
     * Navigation back to the InstructionFragment and clear data if the user wants to go back to
     * the first screen (and do the study again). If the study is done a second time, no data
     * will be saved to the database.
     */
    fun navigateToStart() {
        sharedViewModel.clearData()
        findNavController().navigate(R.id.action_global_to_instruction_fragment)
    }

    /**
     * Show results of the user in a table, including shown icon, correctness of clicked icon
     * and needed time.
     */
    fun navigateToResults() {
        findNavController().navigate(R.id.action_thankYouFragment_to_resultFragment)
    }

    /**
     * Show some statistics to compare the user to other participants.
     */
    fun navigateToStatistics() {
        findNavController().navigate(R.id.action_thankYouFragment_to_statisticsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}