package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.WelcomeFragmentBinding

class WelcomeFragment : Fragment() {
    private var binding: WelcomeFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        print("onCreateView")
        val fragmentBinding = WelcomeFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
        /**
        // Inflate the layout for this fragment
        val view = inflater?.inflate(R.layout.welcome_fragment,
            container, false)

        return view
        **/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            // Set up the button click listeners
            print("onViewCreated")
            startButton.setOnClickListener { startStudy() }
        }
    }

    fun startStudy() {
        print("Button pressed")
        findNavController().navigate(R.id.action_welcomeFragment_to_oneIconFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}