package de.tuchemnitz.iconfinder.model

import androidx.lifecycle.ViewModel
import de.tuchemnitz.iconfinder.R

/**
 * [IconViewModel] holds information about the study, including a list of all icons, the icons the
 * user already saw and data classes used to represent and save the collected data.
 * It also contains the needed methods to work with these informations.
 */
class IconViewModel : ViewModel() {

    /**
     * If data is saved to the database after all four phases, [studyAlreadyDone] is set to
     * true, so the user is not able to do the study again (without closing the app completely).
     */
    private var studyAlreadyDone = false

    /**
     * Returns the Boolean value of [studyAlreadyDone] to indicate whether the study was already
     * done once (all four phases completed and data saved to the database).
     */
    fun getStudyAlreadyDone(): Boolean {
        return studyAlreadyDone
    }

    /**
     * Changes the value of [studyAlreadyDone] to true to indicate that the user has already
     * done the study (completed all four phases).
    */
    fun setStudyAlreadyDone() {
        studyAlreadyDone = true
    }


    /**
     * Phase of the study. There are four different phases 1 to 4.
     */
    private var phase = 1

    /**
     * Returns the current [phase] of the study.
     * Phase 1: The user sees one colorful icon and has to find it in a grid of colorful icons.
     * Phase 2: The user sees one black and white icon and has to find it in a grid of
     * black and white icons.
     * Phase 3: The user sees an icon name and has to find it in a grid of colorful icons.
     * Phase 4: The user sees an icon name and has to find it in a grid of black and white icons.
     */
    fun getPhase(): Int {
        return phase
    }

    /**
     * Sets the [phase] of the study.
     */
    fun setPhase(phaseNr: Int) {
        phase = phaseNr
    }

    /**
     * For every icon there is an [imgId] between 0 and 8,
     * two versions of the icon - a [colorIcon] and [blackWhiteIcon],
     * and the [name] of the icon.
     */
    data class Icon(val imgId: Int, val colorIcon: Int, val blackWhiteIcon: Int, val name: String)

    /**
     * List of all [Icon] objects.
     */
    private val iconList = listOf(
        Icon(0, R.drawable.icon_0, R.drawable.icon_0_bw, "Design"),
        Icon(1, R.drawable.icon_1, R.drawable.icon_1_bw, "Browser"),
        Icon(2, R.drawable.icon_2, R.drawable.icon_2_bw, "Wetter"),
        Icon(3, R.drawable.icon_3, R.drawable.icon_3_bw, "Einstellungen"),
        Icon(4, R.drawable.icon_4, R.drawable.icon_4_bw, "Galerie"),
        Icon(5, R.drawable.icon_5, R.drawable.icon_5_bw, "E-Mail"),
        Icon(6, R.drawable.icon_6, R.drawable.icon_6_bw, "Maps"),
        Icon(7, R.drawable.icon_7, R.drawable.icon_7_bw, "Nachrichten"),
        Icon(8, R.drawable.icon_8, R.drawable.icon_8_bw, "Telefon"),
    )

    /**
     * Returns a [iconList] of all icons used in the study.
     */
    fun getIcons(): List<Icon> {
        return iconList
    }


    /**
     * MutableList of Integer ids of already shown icons.
     */
    private val shownIconsList = mutableListOf<Int>()

    /**
     * Returns [shownIconsList] containing the ids of the icons the user already saw.
     */
    fun getShownIcons(): MutableList<Int> {
        return shownIconsList
    }

    /**
     * Adds a new [iconId] to [shownIconsList].
     */
    fun addShownIcon(iconId: Int) {
        shownIconsList.add(iconId)
    }

    /**
     * Removes all elements from [shownIconsList]
     */
    fun clearShownIcons() {
        shownIconsList.clear()
    }


    /**
     * MutableList that can be shuffled to contain all icons in random order.
     */
    private var shuffleList = mutableListOf<Icon>()

    /**
     * Returns the [shuffleList] containing all icons in random order.
     */
    fun getShuffleList(): MutableList<Icon> {
        return shuffleList
    }

    /**
     * Sets a [shuffledList]. Used for random layout of icons.
     */
    fun setShuffleList(shuffledList: MutableList<Icon>) {
        shuffleList = shuffledList
    }


    /**
     * Image id of the icon that was shown to the user.
     */
    private var shownIcon: Int = 0

    /**
     * Returns the image id of the [shownIcon].
     */
    fun getShownIcon(): Int {
        return shownIcon
    }

    /**
     * Sets the value of the [iconId] the user just saw.
     */
    fun setShownIcon(iconId: Int) {
        shownIcon = iconId
    }


    /**
     * Data as collected in the study. Every data-set consists of
     * the [phase] of the study in which this data was collected,
     * the [iconId] of the icon that was shown to the user,
     * the Boolean value [correctness] indicating whether the user clicked the right icon and
     * the Double [timeNeeded] containing the time the user needed to click the icon.
     */
    data class StudyData(val phase: Int, val iconId: Int, val correctness: Boolean, val timeNeeded: Double)

    /**
     * MutableList of [StudyData]. Needed to save the data to the database.
     */
    private val dataList = mutableListOf<StudyData>()

    /**
     * Returns a [dataList] containing [StudyData].
     */
    fun getData(): MutableList<StudyData> {
        return dataList
    }

    /**
     * Adds new [data] of the type [StudyData] to [dataList].
     */
    fun addData(data: StudyData) {
        dataList.add(data)
    }

    /**
     * Deletes the collected data.
     * Clears [shownIconsList] and [dataList].
     * Sets [shownIcon] and [phase] back to their default values.
     */
    fun clearData() {
        shownIconsList.clear()
        shownIcon = 0
        dataList.clear()
        phase = 1
    }


    /**
     * Data as used in table with results in ResultFragment. Every data-set consists of
     * an Integer containing the [iconId] of the icon the user saw,
     * the Boolean [correct] indicating whether the user clicked the right icon and
     * the String [time] representing the time the user needed to click the icon.
     */
    data class ResultData(val iconId: Int, val correct: Boolean, val time: String)

    /**
     * MutableList of [ResultData]. Needed to display results in table.
     */
    private val resultDataList = mutableListOf<ResultData>()

    /**
     * Returns a list containing [ResultData]. Used in table with results.
     */
    fun getResultData(): MutableList<ResultData> {
        return resultDataList
    }

    /**
     * Adds new [data] of the type [ResultData] to [resultDataList].
     */
    fun addResultData(data: ResultData) {
        resultDataList.add(data)
    }

    /**
     * Selector function needed to sort [resultDataList] ascending by icon ids.
     */
    fun selector(data: ResultData): Int {
        return data.iconId
    }



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
}
