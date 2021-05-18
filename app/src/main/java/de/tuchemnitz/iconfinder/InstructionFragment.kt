package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.InstructionFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel


/**
 * This is the first screen of the IconFinder app.
 * The user is introduced to the app and instructed on how to use it.
 * A button can be pressed to start the study.
 * This screen is also shown with a different instruction text at the beginning of every phase.
 */
class InstructionFragment : Fragment() {

    // Binding object instance corresponding to the instruction_fragment.xml layout
    private var binding: InstructionFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = InstructionFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // instructions for the user
        showInstructions()

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.welcomeFragment = this
    }

    /**
     * Depending on the phase of the study, different instructions are shown at the beginning
     * of the phase.
     */
    private fun showInstructions() {
        when(sharedViewModel.getPhase()) {
            1 -> binding?.instructionText?.setText(R.string.phase1_text)
            2 -> binding?.instructionText?.setText(R.string.phase2_text)
            3 -> binding?.instructionText?.setText(R.string.phase3_text)
            4 -> binding?.instructionText?.setText(R.string.phase4_text)
        }
    }

    /**
     * Navigation to next fragment. User will there see one icon for a few seconds.
     */
    fun startStudy() {
        findNavController().navigate(R.id.action_instructionFragment_to_oneIconFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}