package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.ThankYouFragmentBinding

class ThankYouFragment : Fragment() {
    private var binding: ThankYouFragmentBinding? = null

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
        // used to bind button click listeners in this class to xml
        binding?.thankYouFragment = this
    }

    /**
     * Handles back button press. If not handled, the user would see previous fragment again,
     * which would falsify the study data.
     * Therefore, a back button press navigates the user back to the WelcomeFragment.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                // TODO: delete all the data in the shared view model

                // go back to WelcomeFragment
                findNavController().navigate(R.id.action_global_to_welcome_fragment)
            }
        })
    }

    /**
     * Navigation back to the WelcomeFragment if the user wants to try again.
     */
    fun navigateToStart() {
        // TODO: delete all the data in the shared view model
        findNavController().navigate(R.id.action_global_to_welcome_fragment)
    }

    /**
     * Show results, including shown icon, correctness of clicked icon and needed time.
     */
    fun navigateToResults() {
        findNavController().navigate(R.id.action_thankYouFragment_to_resultFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}