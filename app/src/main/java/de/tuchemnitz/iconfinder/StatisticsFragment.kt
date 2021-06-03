package de.tuchemnitz.iconfinder

import android.os.Bundle
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
    private val numberOfIterationsOnePhase = 9

    private var docCounter = 0
    private var allTimesSum = 0.0
    private var allCorrectsSum = 0.0

    private var allTimesSumPhaseOne = 0.0
    private var allCorrectsSumPhaseOne = 0.0

    private var allTimesSumPhaseTwo = 0.0
    private var allCorrectsSumPhaseTwo = 0.0

    private var allTimesSumPhaseThree = 0.0
    private var allCorrectsSumPhaseThree = 0.0

    private var allTimesSumPhaseFour = 0.0
    private var allCorrectsSumPhaseFour = 0.0



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
        db!!.collection("IconFinder")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                   setStatisticsGeneral(document)
                   setStatisticsPhases(document)
                }
            }
            .addOnCompleteListener {
                bindUserStatistics()
                bindUserStatisticsPhaseOne()
            }
    }

    /**
     * Set values for the statistics for each document in the database.
     */
    private fun setStatisticsGeneral(document: QueryDocumentSnapshot) {
        allTimesSum += document.getDouble("timeNeeded")!!
        docCounter++
        if(document.getBoolean("correctness") == true) {
            allCorrectsSum++
        }
    }

    private fun setStatisticsPhases(document: QueryDocumentSnapshot) {
        when(document.getLong("phase")?.toInt()){
            1 -> {
                allTimesSumPhaseOne += document.getDouble("timeNeeded")!!
                if(document.getBoolean("correctness") == true) {
                    allCorrectsSumPhaseOne++
                }
            }
            2 -> {
                allTimesSumPhaseTwo += document.getDouble("timeNeeded")!!
                if(document.getBoolean("correctness") == true) {
                    allCorrectsSumPhaseTwo++
                }
            }
            3 -> {
                allTimesSumPhaseThree += document.getDouble("timeNeeded")!!
                if(document.getBoolean("correctness") == true) {
                    allCorrectsSumPhaseThree++
                }
            }
            4 -> {
                allTimesSumPhaseFour += document.getDouble("timeNeeded")!!
                if(document.getBoolean("correctness") == true) {
                    allCorrectsSumPhaseFour++
                }
            }
        }
    }

    /**
     * Get statistics related to the user from the shared view model
     * and bind them to statistics_fragment.xml.
     */
    private fun bindUserStatistics() {
        var ownTimeSum = 0.0
        var ownCorrectSum = 0.0

        for(data in sharedViewModel.getData()) {
            ownTimeSum += data.timeNeeded
            if(data.correctness) {
                ownCorrectSum++
            }
        }

        // average time (user)
        val ownTimeAvg = ownTimeSum / numberOfIterations

        // average correctness (user)
        val ownCorrectPercentage = (ownCorrectSum / numberOfIterations) * 100

        // general statistics
        val allTimesAvg = allTimesSum / docCounter
        val allCorrectsPercentage = round((allCorrectsSum / docCounter) * 100)

        binding?.generalStatistics?.text = resources.getString(
            R.string.statistics_general,
            ownTimeAvg,
            ownCorrectPercentage,
            allTimesAvg,
            allCorrectsPercentage
        )

    }

    private fun bindUserStatisticsPhaseOne() {
        var ownTimeSumPhaseOne = 0.0
        var ownCorrectSumPhaseOne = 0.0
        var ownTimeSumPhaseTwo = 0.0
        var ownCorrectSumPhaseTwo = 0.0
        var ownTimeSumPhaseThree = 0.0
        var ownCorrectSumPhaseThree = 0.0
        var ownTimeSumPhaseFour = 0.0
        var ownCorrectSumPhaseFour = 0.0

        for (data in sharedViewModel.getData()) {
            when(data.phase) {
                1 -> {
                    ownTimeSumPhaseOne += data.timeNeeded
                    if (data.correctness) {
                        ownCorrectSumPhaseOne++
                    }
                }
                2 -> {
                    ownTimeSumPhaseTwo += data.timeNeeded
                    if (data.correctness) {
                        ownCorrectSumPhaseTwo++
                    }
                }
                3 -> {
                    ownTimeSumPhaseThree += data.timeNeeded
                    if (data.correctness) {
                        ownCorrectSumPhaseThree++
                    }
                }
                4 -> {
                    ownTimeSumPhaseFour += data.timeNeeded
                    if (data.correctness) {
                        ownCorrectSumPhaseFour++
                    }
                }
            }
        }

        // phase 1
        val ownTimeAvgPhaseOne = ownTimeSumPhaseOne / numberOfIterationsOnePhase
        val ownCorrectPercentagePhaseOne = (ownCorrectSumPhaseOne / numberOfIterationsOnePhase) * 100

        // phase 2
        val ownTimeAvgPhaseTwo = ownTimeSumPhaseTwo / numberOfIterationsOnePhase
        val ownCorrectPercentagePhaseTwo = (ownCorrectSumPhaseTwo / numberOfIterationsOnePhase) * 100

        // phase 3
        val ownTimeAvgPhaseThree = ownTimeSumPhaseThree / numberOfIterationsOnePhase
        val ownCorrectPercentagePhaseThree = (ownCorrectSumPhaseThree / numberOfIterationsOnePhase) * 100

        // phase 4
        val ownTimeAvgPhaseFour = ownTimeSumPhaseFour / numberOfIterationsOnePhase
        val ownCorrectPercentagePhaseFour = (ownCorrectSumPhaseFour / numberOfIterationsOnePhase) * 100


        val docCounterPhases = docCounter / 4

        // statistics phase one
        val allCorrectsPercentagePhaseOne = round((allCorrectsSumPhaseOne / docCounterPhases) * 100)
        val allTimesAvgPhaseOne = allTimesSumPhaseOne / docCounterPhases

        // statistics phase two
        val allCorrectsPercentagePhaseTwo = round((allCorrectsSumPhaseTwo / docCounterPhases) * 100)
        val allTimesAvgPhaseTwo = allTimesSumPhaseTwo / docCounterPhases

        // statistics phase three
        val allCorrectsPercentagePhaseThree = round((allCorrectsSumPhaseThree / docCounterPhases) * 100)
        val allTimesAvgPhaseThree = allTimesSumPhaseThree / docCounterPhases

        // statistics phase four
        val allCorrectsPercentagePhaseFour = round((allCorrectsSumPhaseFour / docCounterPhases) * 100)
        val allTimesAvgPhaseFour = allTimesSumPhaseFour / docCounterPhases

        binding?.phase1Statistics?.text = resources.getString(
            R.string.statistics_phase1,
            ownTimeAvgPhaseOne,
            ownCorrectPercentagePhaseOne,
            allTimesAvgPhaseOne,
            allCorrectsPercentagePhaseOne
        )

        binding?.phase2Statistics?.text = resources.getString(
            R.string.statistics_phase2,
            ownTimeAvgPhaseTwo,
            ownCorrectPercentagePhaseTwo,
            allTimesAvgPhaseTwo,
            allCorrectsPercentagePhaseTwo
        )

        binding?.phase3Statistics?.text = resources.getString(
            R.string.statistics_phase3,
            ownTimeAvgPhaseThree,
            ownCorrectPercentagePhaseThree,
            allTimesAvgPhaseThree,
            allCorrectsPercentagePhaseThree
        )

        binding?.phase4Statistics?.text = resources.getString(
            R.string.statistics_phase4,
            ownTimeAvgPhaseFour,
            ownCorrectPercentagePhaseFour,
            allTimesAvgPhaseFour,
            allCorrectsPercentagePhaseFour
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}