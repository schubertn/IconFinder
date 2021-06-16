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

    // binding object instance corresponding to the one_icon_fragment.xml layout
    private var binding: OneIconFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    // handler needed to navigate to the next fragment after a certain time
    private val iconHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = OneIconFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // the first two phases show the user an icon, the next two phases show the names
        showRandomIcon(sharedViewModel.getPhase())

        // navigation to next fragment after delay
        // TODO: Set delay to 2000ms
        iconHandler.postDelayed({ showAllIcons() }, 500)

        return fragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // handle back button press correctly
        // the user will see the InstructionFragment again and not the previous fragment to
        // avoid falsifying the study data
        // previously collected data is deleted and the user has to start the study again
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
     * Show a random icon or its name depending on the [phase]. Every icon is only shown once.
     * If this is in the first phase of the study the icon will be colorful,
     * if this is in the second phase of the study the icon will be black and white.
     * If this is the third or fourth phase of the study the name of an icon will be displayed.
     */
    private fun showRandomIcon(phase: Int) {
        // generate a random number until the icon associated with this number is one that
        // has not already been shown
        var rnd = (0..8).random()
        while (sharedViewModel.getShownIcons().contains(rnd)) {
            rnd = (0..8).random()
        }

        // set shown icon to later compare it with clicked icon and add it to list of shown icons
        sharedViewModel.setShownIcon(sharedViewModel.getIcons()[rnd].imgId)
        sharedViewModel.addShownIcon(sharedViewModel.getIcons()[rnd].imgId)

        // bind current icon to imageview or its name to textview in one_icon_fragment.xml
        when(phase){
            1 -> binding?.oneIcon?.setImageResource(sharedViewModel.getIcons()[rnd].colorIcon)
            2 -> binding?.oneIcon?.setImageResource(sharedViewModel.getIcons()[rnd].blackWhiteIcon)
            else -> binding?.iconName?.text = sharedViewModel.getIcons()[rnd].name
        }
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