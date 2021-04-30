package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.OneIconFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel

class OneIconFragment : Fragment() {

    private var binding: OneIconFragmentBinding? = null
    private val sharedViewModel: IconViewModel by activityViewModels()
    private val iconHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = OneIconFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // show one random icon
        showRandomIcon()

        // navigate to next fragment after 3 seconds
        iconHandler.postDelayed({ showAllIcons() }, 3000)

        return fragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            // pressing the back button deletes all progress
            override fun handleOnBackPressed() {
                // stop delay
                iconHandler.removeCallbacksAndMessages(null)
                // reset list of shown icons
                sharedViewModel.clearShownIconsList()
                // go back to WelcomeFragment
                findNavController().navigate(R.id.action_global_to_welcome_fragment)
            }
        })
    }

    /**
     * Shows a random icon. Every icon is only shown once.
     */
    private fun showRandomIcon() {
        var rnd = (0..2).random()  // later: (0..8)
        val currentIcon: IconViewModel.Icon // currently displayed icon

        if (sharedViewModel.getShownIcons().size < 3) { // not all icons have been shown
            // generate new number if icon associated with generated number was already shown
            while (sharedViewModel.getShownIcons().contains(rnd)) {
                rnd = (0..2).random()
            }
            currentIcon = sharedViewModel.getAllIcons()[rnd]
            sharedViewModel.addShownIcon(rnd)
        } else {
            currentIcon = sharedViewModel.getAllIcons()[3]
            // now show icons from second list with different icons
            // use e.g. var secondSet = true
        }

        // shown icon to later compare with clicked icon
        sharedViewModel.setShownIcon(currentIcon)
        // bind current icon to one_icon image view
        binding?.oneIcon?.setImageResource(currentIcon.imgId)
    }

    /**
     * Navigation to next fragment to show all icons. The user will have to click the icon
     * seen in this fragment.
     */
    private fun showAllIcons() {
        findNavController().navigate(R.id.action_oneIconFragment_to_allIconsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        // after pressing the home button, delay has to be stopped
        iconHandler.removeCallbacksAndMessages(null)
    }
}