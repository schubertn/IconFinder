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

/**
 * This is the second screen of the IconFinder app.
 * The user sees one icon for a certain time and has to remember it.
 */
class OneIconFragment : Fragment() {

    // Binding object instance corresponding to the one_icon_fragment.xml layout
    private var binding: OneIconFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    // handler needed to show the next fragment after a certain time
    private val iconHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = OneIconFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        showRandomIcon()
        iconHandler.postDelayed({ showAllIcons() }, 1000)

        return fragmentBinding.root
    }

    /**
     * Handles back button press. If not handled, the user would see previous fragment again,
     * which would falsify the study data. Therefore, a back button press navigates the user
     * back to the WelcomeFragment, stops the delay and deletes all data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // stop delay
                iconHandler.removeCallbacksAndMessages(null)
                // reset data
                sharedViewModel.clearData()
                // go back to WelcomeFragment
                findNavController().navigate(R.id.action_global_to_welcome_fragment)
            }
        })
    }

    /**
     * Shows a random icon. Every icon is only shown once.
     */
    private fun showRandomIcon() {
        var rnd = (0..8).random()
        // currently displayed icon
        val currentIcon: IconViewModel.Icon

        if (sharedViewModel.getShownIcons().size < 10) {
            // generate new number if icon associated with generated number was already shown
            while (sharedViewModel.getShownIcons().contains(rnd)) {
                rnd = (0..8).random()
            }
            // set the current icon and add it to the list of shown icons
            currentIcon = sharedViewModel.getAllIcons()[rnd]
            sharedViewModel.addShownIcon(rnd)
        } else {
            currentIcon = sharedViewModel.getAllIcons()[0]
            // only placeholder
            // now show icons from second list with different icons
            // e.g. use var secondSet = true
        }

        // set shown icon to later compare it with clicked icon
        sharedViewModel.setShownIcon(currentIcon)
        // bind current icon to image view in one_icon_fragment.xml
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
        // after pressing the home button, delay has to be stopped, otherwise the app will crash
        iconHandler.removeCallbacksAndMessages(null)
    }
}