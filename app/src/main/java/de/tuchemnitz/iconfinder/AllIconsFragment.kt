package de.tuchemnitz.iconfinder

import android.os.Bundle
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

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = AllIconsFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.
        addCallback(this, object : OnBackPressedCallback(true) {
            // pressing the back button deletes all progress
            override fun handleOnBackPressed() {
                // reset list of shown icons
                sharedViewModel.shownIcons.clear()
                // go back to welcome fragment
                findNavController().navigate(R.id.action_allIconsFragment_to_welcomeFragment)
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            // Set up the button click listeners
            iconButtonOne.setOnClickListener { showOneIcon() }
            iconButtonTwo.setOnClickListener { showOneIcon() }
            iconButtonThree.setOnClickListener { showOneIcon() }
            iconButtonFour.setOnClickListener { showOneIcon() }
            iconButtonFive.setOnClickListener { showOneIcon() }
            iconButtonSix.setOnClickListener { showOneIcon() }
            iconButtonSeven.setOnClickListener { showOneIcon() }
            iconButtonEight.setOnClickListener { showOneIcon() }
            iconButtonNine.setOnClickListener { showOneIcon() }
        }
    }

    private fun showOneIcon() {
        findNavController().navigate(R.id.action_allIconsFragment_to_oneIconFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}