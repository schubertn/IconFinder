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


class StatisticsFragment : Fragment() {

    // TODO: Put this in sharedviewmodel?
    data class CalculationValues(
        var timeSumUser: Double,
        var correctSumUser: Double,
        var timeSumAll: Double,
        var correctSumAll: Double
    ) {
        constructor() : this(0.0, 0.0, 0.0, 0.0)
    }

    data class StatisticsData(
        var timeUser: Double,
        var correctPercentageUser: Double,
        var timeAll: Double,
        var correctPercentageAll: Double
    ) {
        constructor() : this(0.0, 0.0, 0.0, 0.0)
    }

    // binding object instance corresponding to the result_fragment.xml layout
    private var binding: StatisticsFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    // creating a variable for firebase firestore
    private var db: FirebaseFirestore? = null

    private val numberOfIterations = 36
    private val iterationsPerPhase = 9
    private var docCounter = 0

    private var calcValues = Array(5) { CalculationValues() }
    private var statisticValues = Array(5) { StatisticsData() }

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
                    setStatisticsPhases(document, document.getLong("phase")!!.toInt())
                }
            }
            .addOnCompleteListener {
                bindUserStatistics()
                bindUserStatisticsPhaseOne()
                bindGeneralStatistics()
                binding?.statisticsFragment = this
            }
    }

    /**
     * Bind the general statistics to statistics_fragment.xml.
     */
    private fun bindGeneralStatistics() {
        binding?.generalStatistics?.text = resources.getString(
            R.string.statistics_general,
            statisticValues[0].timeUser,
            statisticValues[0].correctPercentageUser,
            statisticValues[0].timeAll,
            statisticValues[0].correctPercentageAll
        )
    }

    /**
     * Set values for the statistics for each document in the database.
     */
    private fun setStatisticsGeneral(document: QueryDocumentSnapshot) {
        calcValues[0].timeSumAll += document.getDouble("timeNeeded")!!
        docCounter++
        if (document.getBoolean("correctness") == true) {
            calcValues[0].correctSumAll++
        }
    }

    private fun setStatisticsPhases(document: QueryDocumentSnapshot, phase: Int) {
        calcValues[phase].timeSumAll += document.getDouble("timeNeeded")!!
        if (document.getBoolean("correctness") == true) {
            calcValues[phase].correctSumAll++
        }
    }


    /**
     * Get statistics related to the user from the shared view model
     * and bind them to statistics_fragment.xml.
     */
    private fun bindUserStatistics() {
        for (data in sharedViewModel.getData()) {
            calcValues[0].timeSumUser += data.timeNeeded
            if (data.correctness) {
                calcValues[0].correctSumUser++
            }
        }

        // average time (user)
        statisticValues[0].timeUser = calcValues[0].timeSumUser / numberOfIterations

        // average correctness (user)
        statisticValues[0].correctPercentageUser =
            (calcValues[0].correctSumUser / numberOfIterations) * 100

        // general statistics
        statisticValues[0].timeAll = calcValues[0].timeSumAll / docCounter
        statisticValues[0].correctPercentageAll = (calcValues[0].correctSumAll / docCounter) * 100
    }


    private fun bindUserStatisticsPhaseOne() {

        for (data in sharedViewModel.getData()) {
            calcValues[data.phase].timeSumUser += data.timeNeeded
            if (data.correctness) {
                calcValues[data.phase].correctSumUser++
            }
        }

        for (i in 1..4) {
            statisticValues[i].timeUser = calcValues[i].timeSumUser / iterationsPerPhase
            statisticValues[i].correctPercentageUser =
                (calcValues[i].correctSumUser / iterationsPerPhase) * 100
        }


        val docCounterPhases = docCounter / 4
        for (i in 1..4) {
            statisticValues[i].timeAll = calcValues[i].timeSumAll / docCounterPhases
            statisticValues[i].correctPercentageAll =
                (calcValues[i].correctSumAll / docCounterPhases) * 100
        }

    }

    fun getStatistics(): Array<StatisticsData> {
        return statisticValues
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}