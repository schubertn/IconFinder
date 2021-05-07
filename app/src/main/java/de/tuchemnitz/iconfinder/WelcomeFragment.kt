package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.WelcomeFragmentBinding

/**
 * This is the first screen of the IconFinder app.
 * The user is introduced to the app and instructed on how to use it.
 * A button can be pressed to start the study.
 */
class WelcomeFragment : Fragment() {

    // Binding object instance corresponding to the welcome_fragment.xml layout
    private var binding: WelcomeFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = WelcomeFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.welcomeFragment = this
    }

    /**
     * Navigation to next fragment. User will there see one icon for a few seconds.
     */
    fun startStudy() {
        findNavController().navigate(R.id.action_welcomeFragment_to_oneIconFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}