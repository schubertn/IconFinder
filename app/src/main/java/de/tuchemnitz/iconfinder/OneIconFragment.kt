package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.OneIconFragmentBinding

class OneIconFragment : Fragment() {

    private var binding: OneIconFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentBinding = OneIconFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            // Set up the button click listeners
            showIconsButton.setOnClickListener { showAllIcons() }
        }
    }

    private fun showAllIcons() {
        findNavController().navigate(R.id.action_oneIconFragment_to_allIconsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}