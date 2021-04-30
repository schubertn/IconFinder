package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.util.Log
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
                sharedViewModel.clearShownIconsList()
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
        // set shuffle list to random order list of elements from all icon list
        sharedViewModel.setShuffleList(sharedViewModel.getAllIcons().shuffled().toMutableList())

        //  alternative: create array containing all buttons and then loop over array ?
        binding?.apply {
            iconButtonZero.setImageResource(sharedViewModel.getShuffleList()[0].imgId)
            iconButtonOne.setImageResource(sharedViewModel.getShuffleList()[1].imgId)
            iconButtonTwo.setImageResource(sharedViewModel.getShuffleList()[2].imgId)
            iconButtonThree.setImageResource(sharedViewModel.getShuffleList()[3].imgId)
            iconButtonFour.setImageResource(sharedViewModel.getShuffleList()[4].imgId)
            iconButtonFive.setImageResource(sharedViewModel.getShuffleList()[5].imgId)
            iconButtonSix.setImageResource(sharedViewModel.getShuffleList()[6].imgId)
            iconButtonSeven.setImageResource(sharedViewModel.getShuffleList()[7].imgId)
            iconButtonEight.setImageResource(sharedViewModel.getShuffleList()[8].imgId)
        }
    }

    /**
     * Tests if the clicked icon is the same as the shown icon.
     * For now only prints the result to the LogCat.
     */
    fun onIconClicked(buttonNumber: Int) {
        if (sharedViewModel.getShuffleList()[buttonNumber] == sharedViewModel.getShownIcon()) {
            Log.d("TESTING", "correct icon")
        } else {
            Log.d("TESTING", "wrong icon")
        }

        // go back to previous fragment
        showOneIcon()
    }

    /**
     * Navigation to previous fragment to show only one icon. The user will have to look at it
     * for a certain amount of time.
     */
    private fun showOneIcon() {
        findNavController().navigate(R.id.action_allIconsFragment_to_oneIconFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}