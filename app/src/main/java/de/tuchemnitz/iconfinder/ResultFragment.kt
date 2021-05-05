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

        generateResultData()


        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            iconViewModel = sharedViewModel
        }
    }

    private fun generateResultData() {
        for (data in sharedViewModel.getData()) {
            sharedViewModel.addResultData(IconViewModel.ResultData(
                data.shownIcon.imgId,
                data.correctness.toString(),
                data.timeNeeded.toString()
            ))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}