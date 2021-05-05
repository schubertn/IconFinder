package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.tuchemnitz.iconfinder.databinding.ResultFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel

class ResultFragment : Fragment() {
    private var binding: ResultFragmentBinding? = null
    private val sharedViewModel: IconViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = ResultFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // convert data to a format suitable for result table
        generateResultData()

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // needed to bind data to xml file
        binding?.apply { iconViewModel = sharedViewModel }
    }

    /**
     * Convert data to a format suitable for result table.
     * Instead of Icon use Int containing icon id to show the corresponding image.
     * Instead of a Boolean value use a String to indicate correctness.
     * Instead of a Double use a String for the time in seconds.
     */
    private fun generateResultData() {
        for (data in sharedViewModel.getData()) {
            sharedViewModel.addResultData(IconViewModel.ResultData(
                data.shownIcon.imgId,
                data.correctness.toCustomString(),
                data.timeNeeded.toCustomString()
            ))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

/**
 * Custom toString() function to return time with added "Sekunden".
 */
private fun Double.toCustomString(): String {
    return "$this Sekunden"
}

/**
 * Custom toString() function to either return "ja" or "nein" instead of true/false.
 */
private fun Boolean.toCustomString(): String {
    return if(this){
        "ja"
    } else {
        "nein"
    }
}

