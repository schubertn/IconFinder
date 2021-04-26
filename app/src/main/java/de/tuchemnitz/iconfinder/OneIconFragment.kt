package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.tuchemnitz.iconfinder.databinding.OneIconFragmentBinding
import de.tuchemnitz.iconfinder.model.OrderViewModel

class OneIconFragment : Fragment() {

    private var binding: OneIconFragmentBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = OneIconFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        showRandomIcon()

        return fragmentBinding.root
    }

    /**
     * Shows a random icon. Every icon is only shown once.
     */
    private fun showRandomIcon() {
        var rnd = (0..2).random()  // later: (0..8)
        val currentIcon: OrderViewModel.Icon

        if (sharedViewModel.shownIcons.size < 3) { // not all icons have been shown
            while (sharedViewModel.shownIcons.contains(rnd)){
                rnd = (0..2).random()
            }
            currentIcon = sharedViewModel.allIcons[rnd]
            sharedViewModel.addShownIcon(rnd)
        } else {
            currentIcon = sharedViewModel.allIcons[3]
            // now show icons from second list with different icons
            // use e.g. var secondSet = true
        }

        // bind current icon to one_icon image view
        binding?.oneIcon?.setImageResource(currentIcon.imgId)
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

    /** that's how a binding adapter would work
    @BindingAdapter("android:src")
    fun setImageViewResource(imageView: ImageView, resource: Int) {
        val rnd = (1..3).random()
        val resName = "img_" + rnd
        var newIcon = allIcons[0]
        binding?.imageView?.setImageResource(newIcon.imgId)
    }
    **/

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}