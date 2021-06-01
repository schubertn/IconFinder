package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.FirebaseFirestore
import de.tuchemnitz.iconfinder.databinding.StatisticsFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel


class StatisticsFragment : Fragment() {

    // binding object instance corresponding to the result_fragment.xml layout
    private var binding: StatisticsFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    // creating a variable for firebase firestore
    private var db: FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = StatisticsFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        testQuery()

        return fragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // getting our instance from firebase firestore
        db = FirebaseFirestore.getInstance()
    }

    private fun testQuery() {
        /**
         * val test = db?.collection("IconFinder")
         * val query = test?.whereEqualTo("iconId", 1)?.get()
         * **/

        var timeSum = 0.0
        var docCounter = 0
        var correctSum = 0
        var falseSum = 0
        db!!.collection("IconFinder")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("test", "${document.id} => ${document.data}")
                    timeSum += document.getDouble("timeNeeded")!!
                    docCounter++
                    if(document.getBoolean("correctness") == false) {
                        falseSum++
                    } else {
                        correctSum++
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("test", "Error getting documents: ", exception)
            }
            .addOnCompleteListener {
                val timeAvg = timeSum / docCounter
                Log.d("test", "Sum time is $timeSum")
                Log.d("test", "Counter is $docCounter")
                Log.d("test", "Average time is $timeAvg")
                binding?.timeAvg?.text = timeAvg.toString()
                binding?.correctSum?.text = correctSum.toString()
                binding?.falseSum?.text = falseSum.toString()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}