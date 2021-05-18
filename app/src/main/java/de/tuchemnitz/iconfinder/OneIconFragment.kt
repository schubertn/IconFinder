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
 * The user sees one icon or the name of an icon for a certain time and has to remember it.
 * In the first phase of the study the icons are colorful,
 * in the second phase they are black and white,
 * and in the third and fourth phase only icon names are shown.
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

        // the first two phases show the user an icon, the next two phases show the names
        if (sharedViewModel.getPhase() < 3) {
            showRandomIcon()
        } else {
            showRandomIconName()
        }

        // navigation to next fragment after delay
        iconHandler.postDelayed({ showAllIcons() }, 1000)

        return fragmentBinding.root
    }

    /**
     * Handles back button press. If not handled, the user would see previous fragment again,
     * which would falsify the study data. Therefore, a back button press navigates the user
     * back to the InstructionFragment, stops the delay and deletes all data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // stop delay
                iconHandler.removeCallbacksAndMessages(null)
                // reset data
                sharedViewModel.clearData()
                // go back to InstructionFragment
                findNavController().navigate(R.id.action_global_to_instruction_fragment)
            }
        })
    }

    /**
     * Shows a random icon. Every icon is only shown once.
     * If this is in the first phase of the study the icon will be colorful,
     * if this is in the second phase of the study the icon will be black and white.
     */
    private fun showRandomIcon() {
        var rnd = (0..8).random()
        // currently displayed icon
        val currentIcon: IconViewModel.Icon

        // generate new number if icon associated with generated number was already shown
        while (sharedViewModel.getShownIcons().contains(rnd)) {
            rnd = (0..8).random()
        }

        // set the current icon and add it to the list of shown icons
        // in phase 1 the current icon is colored, in phase 2 it is black and white
        if (sharedViewModel.getPhase() == 1) {
            currentIcon = sharedViewModel.getColorIcons()[rnd]
        } else {
            currentIcon = sharedViewModel.getBlackWhiteIcons()[rnd]
        }

        // add the index of the icon to the list of icons that have already been shown
        sharedViewModel.addShownIcon(rnd)

        sharedViewModel.setShownIconId(rnd)

        // set shown icon to later compare it with clicked icon
        sharedViewModel.setShownIcon(currentIcon)
        // bind current icon to imageview in one_icon_fragment.xml
        binding?.oneIcon?.setImageResource(currentIcon.imgId)
    }

    /**
     * Shows a random icon name. Every name is shown once in phase 3 and once in phase 4.
     * The phase of the study has no impact on which names are shown.
     */
    private fun showRandomIconName() {
        var rnd = (0..8).random()
        // generate new number if icon associated with generated number was already shown
        while (sharedViewModel.getShownIcons().contains(rnd)) {
            rnd = (0..8).random()
        }

        // set the current icon and add its index to the list of shown icons
        val currentIconName = sharedViewModel.getIconNames()[rnd]
        sharedViewModel.addShownIcon(rnd)

        // icon id that is used for storing the data
        sharedViewModel.setShownIconId(rnd)

        // set shown icon to later compare it with clicked icon
        // this uses the actual icon and not its name to allow the comparison with the clicked icon
        if (sharedViewModel.getPhase() == 3) {
            sharedViewModel.setShownIcon(sharedViewModel.getColorIcons()[rnd])
        } else {
            sharedViewModel.setShownIcon(sharedViewModel.getBlackWhiteIcons()[rnd])
        }
        // bind current icon name to textview in one_icon_fragment.xml
        binding?.iconName?.text = currentIconName
    }


    /**
     * Navigation to next fragment to show all icons. The user will have to click the icon/name
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