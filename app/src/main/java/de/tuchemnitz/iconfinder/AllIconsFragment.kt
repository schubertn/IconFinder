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

        // show all icons in random order
        showRandomOrderIcons()

        return fragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            // pressing the back button deletes all progress
            override fun handleOnBackPressed() {
                // reset list of shown icons
                sharedViewModel.shownIcons.clear()
                // go back to WelcomeFragment
                findNavController().navigate(R.id.action_global_to_welcome_fragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // used to bind button click listeners in this class to xml
        binding?.allIconsFragment = this
    }

    /**
     * Shows the nine icons in random order on the 3x3 grid.
     */
    private fun showRandomOrderIcons() {

        val randomizedList = sharedViewModel.allIcons.shuffled()

        binding?.apply {
            iconButtonOne.setImageResource(randomizedList[0].imgId)
            iconButtonTwo.setImageResource(randomizedList[1].imgId)
            iconButtonThree.setImageResource(randomizedList[2].imgId)
            iconButtonFour.setImageResource(randomizedList[3].imgId)
            iconButtonFive.setImageResource(randomizedList[4].imgId)
            iconButtonSix.setImageResource(randomizedList[5].imgId)
            iconButtonSeven.setImageResource(randomizedList[6].imgId)
            iconButtonEight.setImageResource(randomizedList[7].imgId)
            iconButtonNine.setImageResource(randomizedList[8].imgId)
        }
    }

    /**
     * Navigation to previous fragment to show only one icon. The user has to look at it
     * for a certain amount of time.
     */
    fun showOneIcon() {
        findNavController().navigate(R.id.action_allIconsFragment_to_oneIconFragment)
        /**
         * vielleicht neue funktion onButtonPressed(buttonNumber), die diese hier aufruft und
         * außerdem testet und speichert ob geklickter button der richtige war
         */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}