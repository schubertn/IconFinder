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
import de.tuchemnitz.iconfinder.model.StatisticsViewModel

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

class StatisticsFragment : Fragment() {

    // binding object instance corresponding to the result_fragment.xml layout
    private var binding: StatisticsFragmentBinding? = null

    private val sharedViewModelStudy: IconViewModel by activityViewModels()
    private val sharedViewModelStatistics: StatisticsViewModel by activityViewModels()

    // creating a variable for firebase firestore
    private var db: FirebaseFirestore? = null

    private var generalCalcValues = CalculationValues()
    private var phaseOneCalcValues = CalculationValues()
    private var phaseTwoCalcValues = CalculationValues()
    private var phaseThreeCalcValues = CalculationValues()
    private var phaseFourCalcValues = CalculationValues()

    private val numberOfIterations = 36
    private val iterationsPerPhase = 9
    private var docCounter = 0

    private var generalStatistics = StatisticsData()
    private var phaseOneStatistics = StatisticsData()
    private var phaseTwoStatistics = StatisticsData()
    private var phaseThreeStatistics = StatisticsData()
    private var phaseFourStatistics = StatisticsData()

    private var calcValues = arrayOf(
        generalCalcValues,
        phaseOneCalcValues,
        phaseTwoCalcValues,
        phaseThreeCalcValues,
        phaseFourCalcValues
    )

    
    private var statisticValues = arrayOf(
        generalStatistics,
        phaseOneStatistics,
        phaseTwoStatistics,
        phaseThreeStatistics,
        phaseFourStatistics
    )


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
            generalStatistics.timeUser,
            generalStatistics.correctPercentageUser,
            generalStatistics.timeAll,
            generalStatistics.correctPercentageAll
        )
    }

    /**
     * Set values for the statistics for each document in the database.
     */
    private fun setStatisticsGeneral(document: QueryDocumentSnapshot) {
        generalCalcValues.timeSumAll += document.getDouble("timeNeeded")!!
        docCounter++
        if (document.getBoolean("correctness") == true) {
            generalCalcValues.correctSumAll++
        }
    }

    private fun setStatisticsPhases(document: QueryDocumentSnapshot, phase: Int) {
        calcValues[phase].timeSumAll += document.getDouble("timeNeeded")!!
        if (document.getBoolean("correctness") == true) {
            calcValues[phase].correctSumAll++
        }
        /**
        when (document.getLong("phase")?.toInt()) {
            1 -> {
                phaseOneCalcValues.timeSumAll += document.getDouble("timeNeeded")!!
                if (document.getBoolean("correctness") == true) {
                    phaseOneCalcValues.correctSumAll++
                }
            }
            2 -> {
                phaseTwoCalcValues.timeSumAll += document.getDouble("timeNeeded")!!
                if (document.getBoolean("correctness") == true) {
                    phaseTwoCalcValues.correctSumAll++
                }
            }
            3 -> {
                phaseThreeCalcValues.timeSumAll += document.getDouble("timeNeeded")!!
                if (document.getBoolean("correctness") == true) {
                    phaseThreeCalcValues.correctSumAll++
                }
            }
            4 -> {
                phaseFourCalcValues.timeSumAll += document.getDouble("timeNeeded")!!
                if (document.getBoolean("correctness") == true) {
                    phaseFourCalcValues.correctSumAll++
                }
            }
        }
        **/
    }


    /**
     * Get statistics related to the user from the shared view model
     * and bind them to statistics_fragment.xml.
     */
    private fun bindUserStatistics() {
        for (data in sharedViewModelStudy.getData()) {
            generalCalcValues.timeSumUser += data.timeNeeded
            if (data.correctness) {
                generalCalcValues.correctSumUser++
            }
        }

        // average time (user)
        generalStatistics.timeUser = generalCalcValues.timeSumUser / numberOfIterations

        // average correctness (user)
        generalStatistics.correctPercentageUser = (generalCalcValues.correctSumUser / numberOfIterations) * 100

        // general statistics
        generalStatistics.timeAll = generalCalcValues.timeSumAll / docCounter
        generalStatistics.correctPercentageAll = (generalCalcValues.correctSumAll / docCounter) * 100

    }


    private fun bindUserStatisticsPhaseOne() {

        for (data in sharedViewModelStudy.getData()) {
            calcValues[data.phase].timeSumUser += data.timeNeeded
            if (data.correctness) {
                calcValues[data.phase].correctSumUser++
            }
            /**
            when (data.phase) {
                1 -> {
                    phaseOneCalcValues.timeSumUser += data.timeNeeded
                    if (data.correctness) {
                        phaseOneCalcValues.correctSumUser++
                    }
                }
                2 -> {
                    phaseTwoCalcValues.timeSumUser += data.timeNeeded
                    if (data.correctness) {
                        phaseTwoCalcValues.correctSumUser++
                    }
                }
                3 -> {
                    phaseThreeCalcValues.timeSumUser += data.timeNeeded
                    if (data.correctness) {
                        phaseThreeCalcValues.correctSumUser++
                    }
                }
                4 -> {
                    phaseFourCalcValues.timeSumUser += data.timeNeeded
                    if (data.correctness) {
                        phaseFourCalcValues.correctSumUser++
                    }
                }
            }
            **/
        }

        for(i in 1..4){
            statisticValues[i].timeUser = calcValues[i].timeSumUser / iterationsPerPhase
            statisticValues[i].correctPercentageUser = (calcValues[i].correctSumUser / iterationsPerPhase) * 100
        }

        /**
        // user statistics phase 1
        phaseOneStatistics.timeUser = phaseOneCalcValues.timeSumUser / iterationsPerPhase
        phaseOneStatistics.correctPercentageUser = (phaseOneCalcValues.correctSumUser / iterationsPerPhase) * 100

        // user statistics phase 2
        phaseTwoStatistics.timeUser = phaseTwoCalcValues.timeSumUser / iterationsPerPhase
        phaseTwoStatistics.correctPercentageUser = (phaseTwoCalcValues.correctSumUser / iterationsPerPhase) * 100

        // user statistics phase 3
        phaseThreeStatistics.timeUser = phaseThreeCalcValues.timeSumUser / iterationsPerPhase
        phaseThreeStatistics.correctPercentageUser = (phaseThreeCalcValues.correctSumUser / iterationsPerPhase) * 100

        // user statistics phase 4
        phaseFourStatistics.timeUser = phaseFourCalcValues.timeSumUser / iterationsPerPhase
        phaseFourStatistics.correctPercentageUser = (phaseFourCalcValues.correctSumUser / iterationsPerPhase) * 100
        **/


        val docCounterPhases = docCounter / 4
        for(i in 1..4){
            statisticValues[i].timeAll = calcValues[i].timeSumAll / docCounterPhases
            statisticValues[i].correctPercentageAll = (calcValues[i].correctSumAll / docCounterPhases) * 100
        }


        /**
        // statistics phase one
        phaseOneStatistics.correctPercentageAll =
            (phaseOneCalcValues.correctSumAll / docCounterPhases) * 100
        phaseOneStatistics.timeAll = phaseOneCalcValues.timeSumAll / docCounterPhases

        // statistics phase two
        phaseTwoStatistics.correctPercentageAll =
            (phaseTwoCalcValues.correctSumAll / docCounterPhases) * 100
        phaseTwoStatistics.timeAll = phaseTwoCalcValues.timeSumAll / docCounterPhases

        // statistics phase three
        phaseThreeStatistics.correctPercentageAll =
            (phaseThreeCalcValues.correctSumAll / docCounterPhases) * 100
        phaseThreeStatistics.timeAll = phaseTwoCalcValues.timeSumAll / docCounterPhases

        // statistics phase four
        phaseFourStatistics.correctPercentageAll =
            (phaseFourCalcValues.correctSumAll / docCounterPhases) * 100
        phaseFourStatistics.timeAll = phaseTwoCalcValues.timeSumAll / docCounterPhases
        **/
    }



    fun getPhaseOneStatistics() : StatisticsData {
        return phaseOneStatistics
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}