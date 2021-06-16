package de.tuchemnitz.iconfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import de.tuchemnitz.iconfinder.databinding.StatisticsFragmentBinding
import de.tuchemnitz.iconfinder.model.IconViewModel

/**
 * This study consists of 36 individual iterations, in which the user sees an icon or its name
 * and has to click the corresponding icon.
 */
private const val NUM_OF_ITERATIONS = 36

/**
 * This is one of the two last screens of the IconFinder app.
 * The user will see statistics related to the study in a table and a text and button below.
 * Each row contains the results concerning one phase of the study as displayed in the first column.
 * The other two columns split the statistics in those related to the user and those related to
 * all participants.
 * The data from the other participants is loaded from the database. Until this process and the
 * calculations are finished, the user sees a progressbar.
 * The shown statistics are the average time to click one icon and the percentage of correctly
 * clicked icons, classified by phase and user or all participants.
 * The text below the table sums up those statistics over all phases.
 * The button below the text allows the user to navigate back to the previous screen.
 */
class StatisticsFragment : Fragment() {

    // binding object instance corresponding to the result_fragment.xml layout
    private var binding: StatisticsFragmentBinding? = null

    private val sharedViewModel: IconViewModel by activityViewModels()

    // creating a variable for firebase firestore
    private var db: FirebaseFirestore? = null

    /**
     * An Array with five [IconViewModel.CalculationValues] elements.
     * For each element the values needed to later calculate the statistics are stored.
     * The element at index zero corresponds to the general data, indices 1-4 correspond to
     * the equivalent phases.
     */
    private var calcValues = Array(5) { IconViewModel.CalculationValues() }

    /**
     * An Array with five [IconViewModel.StatisticsData] elements.
     * For each element the statistical values relevant to the user are stored.
     * The element at index zero corresponds to the general data, indices 1-4 correspond to
     * the equivalent phases.
     */
    private var statisticValues = Array(5) { IconViewModel.StatisticsData() }

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
        // get instance from firebase firestore
        db = FirebaseFirestore.getInstance()
    }

    /**
     * Get values related to all users from the database.
     * Once the queries are completed, the statistics are calculated and set to be used in the
     * statistics table.
     */
    private fun getAllStatistics() {
        var docCounter = 0 // counts the number of documents in the database

        db!!.collection("IconFinder")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    docCounter++
                    setCalculationValuesDatabase(document, document.getLong("phase")!!.toInt())
                }
            }
            .addOnCompleteListener {
                // set values needed for calculations related to the user
                setCalculationValuesUser()
                // set values needed for calculation of the general statistics
                setCalculationValuesGeneral()

                // set the statistics for phases 1-4
                val numDocsPerPhase = docCounter / 4
                setStatisticsPhases(numDocsPerPhase)

                // set the general statistics and bind them to the xml
                setStatisticsGeneral(docCounter)
                bindGeneralStatistics()

                // after the calculations are done, display the statistics
                binding?.statisticsFragment = this
                showStatistics()
            }
    }

    /**
     * For each [document] in the database this method is called.
     * For every [phase] the times needed to click the icon are added, as well as the number of
     * correct icons.
     */
    private fun setCalculationValuesDatabase(document: QueryDocumentSnapshot, phase: Int) {
        calcValues[phase].timeSumAll += document.getDouble("timeNeeded")!!
        if (document.getBoolean("correctness") == true) {
            calcValues[phase].correctSumAll++
        }
    }

    /**
     * Set the values needed for the calculations related to the user.
     * For each phase the times needed and the number of correct icons as stored in the
     * [IconViewModel] are added up.
     */
    private fun setCalculationValuesUser() {
        for (data in sharedViewModel.getData()) {
            calcValues[data.phase].timeSumUser += data.timeNeeded
            if (data.correctness) {
                calcValues[data.phase].correctSumUser++
            }
        }
    }

    /**
     * Set the values for the calculation of the general statistics.
     * The sum of the times needed and correct icons from each phase previously extracted from the
     * database are added up.
     */
    private fun setCalculationValuesGeneral() {
        for (i in 1..4) {
            // set general calculation values for database data
            calcValues[0].timeSumAll += calcValues[i].timeSumAll
            calcValues[0].correctSumAll += calcValues[i].correctSumAll

            // set general calculation values for user data
            calcValues[0].timeSumUser += calcValues[i].timeSumUser
            calcValues[0].correctSumUser += calcValues[i].correctSumUser
        }
    }

    /**
     * Set the statistics for the phases 1-4.
     * For each phase the average time the user needed and the percentage of correct icons are
     * calculated with the calculation values.
     * The average time and percentage of correct icons per phase from all users from the database
     * are also calculated using the [numDocsPerPhase].
     */
    private fun setStatisticsPhases(numDocsPerPhase: Int) {
        for (i in 1..4) {
            // set the statistic values for the data from the user
            statisticValues[i].timeUser =
                calcValues[i].timeSumUser / (NUM_OF_ITERATIONS / 4) //iterationsPerPhase
            statisticValues[i].correctPercentUser =
                (calcValues[i].correctSumUser / (NUM_OF_ITERATIONS / 4)) * 100

            // set the statistic values for the data from the database
            statisticValues[i].timeAll = calcValues[i].timeSumAll / numDocsPerPhase
            statisticValues[i].correctPercentAll =
                (calcValues[i].correctSumAll / numDocsPerPhase) * 100
        }
    }

    /**
     * Set the general statistics for user and database data.
     * For the user and the database the average time and the percentage of correct icons
     * are calculated. For the calculations related to the database the [docCounter] containing
     * the number of documents is needed.
     */
    private fun setStatisticsGeneral(docCounter: Int) {
        // set the general statistics for the user
        statisticValues[0].timeUser = calcValues[0].timeSumUser / NUM_OF_ITERATIONS
        statisticValues[0].correctPercentUser =
            (calcValues[0].correctSumUser / NUM_OF_ITERATIONS) * 100

        // set general statistics for the database
        statisticValues[0].timeAll = calcValues[0].timeSumAll / docCounter
        statisticValues[0].correctPercentAll = (calcValues[0].correctSumAll / docCounter) * 100
    }

    /**
     * Bind the general statistics to statistics_fragment.xml.
     */
    private fun bindGeneralStatistics() {
        binding?.generalStatistics?.text = resources.getString(
            R.string.statistics_general,
            statisticValues[0].timeUser,
            statisticValues[0].correctPercentUser,
            statisticValues[0].timeAll,
            statisticValues[0].correctPercentAll
        )
    }

    /**
     * Returns [statisticValues] to be used in the statistics table in statistics_fragment.xml.
     */
    fun getStatistics(): Array<IconViewModel.StatisticsData> {
        return statisticValues
    }

    /**
     * Hide the previously displayed progessbar and
     * display the statistics in a table after they are calculated.
     */
    private fun showStatistics() {
        binding!!.progressBar.visibility = View.GONE
        binding!!.linearLayout.visibility = View.VISIBLE
    }

    /**
     * On button click navigate back to the previous screen, where the user can decide what to do
     * after reaching the end of the study.
     */
    fun backToPreviousScreen() {
        findNavController().navigate(R.id.action_statisticsFragment_to_thankYouFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}