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

    data class Icon(val imgId: Int)

    private val allIcons = listOf(
            Icon(de.tuchemnitz.iconfinder.R.drawable.img_1),
            Icon(de.tuchemnitz.iconfinder.R.drawable.img_2),
            Icon(de.tuchemnitz.iconfinder.R.drawable.img_3)
    )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val fragmentBinding = OneIconFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        val rnd = (0..2).random()
        var newIcon = allIcons[rnd]
        binding?.imageView?.setImageResource(newIcon.imgId)

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

    /**
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

/**
class OneIconFragment : Fragment() {

    private var binding: OneIconFragmentBinding? = null

    data class Icon(val imgId: Int)

        private val allIcons = listOf(
                Icon(de.tuchemnitz.iconfinder.R.drawable.img_1),
                Icon(de.tuchemnitz.iconfinder.R.drawable.img_2),
                Icon(de.tuchemnitz.iconfinder.R.drawable.img_3)
        )


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

    @BindingAdapter("android:src")
    fun setImageViewResource(imageView: ImageView, resource: Int) {
        val rnd = (1..3).random()
        val resName = "img_" + rnd
        var newIcon = allIcons[0]
        binding?.imageView?.setImageResource(newIcon.imgId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
**/