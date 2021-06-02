package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import de.tuchemnitz.iconfinder.databinding.StatisticsFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel
import kotlin.math.round


class StatisticsFragment : Fragment() {

    // binding object instance corresponding to the result_fragment.xml layout
    private var binding: StatisticsFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    // creating a variable for firebase firestore
    private var db: FirebaseFirestore? = null

    private val numberOfIterations = 36

    private var docCounter = 0
    private var allTimesSum = 0.0
    private var allCorrectsSum = 0.0

    private var allTimesPhaseOneSum = 0.0
    private var allCorrectsPhaseOneSum = 0.0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = StatisticsFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        getAllStatistics()

        return fragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // getting our instance from firebase firestore
        db = FirebaseFirestore.getInstance()
    }

    /**
     * Get statistics related to all users from the database.
     */
    private fun getAllStatistics() {
        /**
         * val test = db?.collection("IconFinder")
         * val query = test?.whereEqualTo("iconId", 1)?.get()
         * **/

        db!!.collection("IconFinder")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                   setStatistics(document)
                   setStatisticsPhase1(document)


                }
            }
            .addOnFailureListener { exception ->
                Log.d("test", "Error getting documents: ", exception)
            }
            .addOnCompleteListener {
                Log.d("test", "Binding")
                bindDatabaseStatistics()
                bindUserStatistics()
                bindUserStatisticsPhaseOne()
            }
    }

    /**
     * Set values for the statistics for each document in the database.
     */
    private fun setStatistics(document: QueryDocumentSnapshot) {
        allTimesSum += document.getDouble("timeNeeded")!!
        docCounter++
        if(document.getBoolean("correctness") == true) {
            allCorrectsSum++
        }
    }

    private fun setStatisticsPhase1(document: QueryDocumentSnapshot) {
        if(document.getLong("phase")?.toInt() == 1) {
            Log.d("test", "this is phase 1")
            allTimesPhaseOneSum += document.getDouble("timeNeeded")!!
            if(document.getBoolean("correctness") == true) {
                allCorrectsPhaseOneSum++
            }
        }
    }

    // setStatisticsPhase2()
    // setStatisticsPhase3()
    // setStatisticsPhase4()

    /**
     * Bind the statistics from the database to statistics_fragment.xml to show them to the user.
     */
    private fun bindDatabaseStatistics() {
        // average time (all users)
        val allTimesAvg = allTimesSum / docCounter
        binding?.allTimesAvg?.text = String.format(resources.getString(R.string.test_string), allTimesAvg)

        val allTimesPhaseOneAvg = allTimesPhaseOneSum / docCounter
        binding?.allTimesPhase1Avg?.text = resources.getString(R.string.test_string, allTimesPhaseOneAvg)

        // average correctness (all users)
        val allCorrectsPercentage = round((allCorrectsSum / docCounter) * 100)
        binding?.allCorrectsAvg?.text = allCorrectsPercentage.toString()

        val allCorrectsPhaseOnePercentage = round((allCorrectsPhaseOneSum / docCounter) * 100)
        binding?.allCorrectsPhase1Avg?.text = allCorrectsPhaseOnePercentage.toString()

        Log.d("test", "Sum time is $allTimesSum")
        Log.d("test", "Counter is $docCounter")
        Log.d("test", "Average time is $allTimesAvg")
        Log.d("test", "Average time Phase One $allTimesPhaseOneAvg")
        Log.d("test", "Sum of correct is $allCorrectsSum")
        Log.d("test", "Average correct is $allCorrectsPercentage")
        Log.d("test", "Sum of correct phase one is $allCorrectsPhaseOneSum")
        Log.d("test", "Average correct phase 1 is $allCorrectsPhaseOnePercentage")
    }


    /**
     * Get statistics related to the user from the shared view model
     * and bind them to statistics_fragment.xml.
     */
    private fun bindUserStatistics() {
        Log.d("test", "User binding")

        var ownTimeSum = 0.0
        var ownCorrectSum = 0

        for(data in sharedViewModel.getData()) {
            ownTimeSum += data.timeNeeded
            if(data.correctness) {
                ownCorrectSum++
            }
        }

        // average time (user)
        val ownTimeAvg = ownTimeSum / numberOfIterations
        binding?.timeAvg?.text = ownTimeAvg.toString()

        // average correctness (user)
        val ownCorrectPercentage = ((ownCorrectSum / numberOfIterations) * 100).toDouble()
        binding?.correctAvg?.text = ownCorrectPercentage.toString()

        Log.d("test", "Time General = $ownTimeAvg")
        Log.d("test", "Correct General = $ownCorrectPercentage")
    }

    private fun bindUserStatisticsPhaseOne() {
        Log.d("test", "User binding")

        var ownTimeSumPhaseOne = 0.0
        var ownCorrectSumPhaseOne = 0

        for (data in sharedViewModel.getData()) {
            if (data.phase == 1) {
                ownTimeSumPhaseOne += data.timeNeeded
                if (data.correctness) {
                    ownCorrectSumPhaseOne++
                }
            }
        }

        val ownTimeAvgPhaseOne = ownTimeSumPhaseOne / numberOfIterations
        binding?.timePhase1Avg?.text = ownTimeAvgPhaseOne.toString()

        val ownCorrectPercentagePhaseOne = ((ownCorrectSumPhaseOne / numberOfIterations) * 100).toDouble()
        binding?.correctPhase1Avg?.text = ownCorrectPercentagePhaseOne.toString()

        Log.d("test", "Time Phase 1 = $ownTimeAvgPhaseOne")
        Log.d("test", "Correct Phase 1 = $ownCorrectPercentagePhaseOne")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}