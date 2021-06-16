package de.tuchemnitz.iconfinder.model

import androidx.lifecycle.ViewModel
import de.tuchemnitz.iconfinder.R

/**
 * The [IconViewModel] holds information about the study, including a list of all icons,
 * the icons the user already saw and data classes used to represent and save the collected data.
 * It also contains the needed methods to work with these informations.
 */
class IconViewModel : ViewModel() {

    /**
     * If data is saved to the database after all four phases, this value is set to
     * true, so the user is not able to do the study again (without closing the app completely).
     */
    private var studyAlreadyDone = false

    /**
     * Returns the Boolean value of [studyAlreadyDone] to indicate whether the study was already
     * done once (all four phases completed and data saved to the database) or not.
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
     * [List] of all [Icon] objects.
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
     * [MutableList] of Integer ids of already shown icons.
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
     * [MutableList] that can be shuffled to contain all icons in random order.
     */
    private var shuffleList = mutableListOf<Icon>()

    /**
     * Returns the [shuffleList] containing all icons in random order.
     */
    fun getShuffleList(): MutableList<Icon> {
        return shuffleList
    }

    /**
     * Sets a [shuffleList]. Used for random layout of icons.
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
     * Sets the value of the [shownIcon] the user just saw.
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
    data class StudyData(
        val phase: Int,
        val iconId: Int,
        val correctness: Boolean,
        val timeNeeded: Double
    )

    /**
     * [MutableList] of [StudyData]. Needed to save the data to the database.
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
     * [List] of [StudyData] to be sorted ascending by the icon ids and phases.
     */
    private var sortedDataList = listOf<StudyData>()

    /**
     * Fills [sortedDataList] with a [sortedList] of [StudyData].
     */
    fun setSortedData(sortedList: List<StudyData>) {
        sortedDataList = sortedList
    }

    /**
     * Returns [sortedDataList] containing [StudyData] sorted by the icon ids and phases.
     */
    fun getSortedData(): List<StudyData> {
        return sortedDataList
    }

    /**
     * Selector function needed to sort [sortedDataList] ascending by icon ids.
     */
    fun selector(data: StudyData): Int {
        return data.iconId
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
     * Values needed for the calculation of the statistics can be grouped so that related values
     * can be stored as one object. The values stored are
     * the [timeSumUser] containing the sum of the times needed to click an icon by the user,
     * the [correctSumUser] corresponding to the sum of correctly clicked icons by the user,
     * the [timeSumAll] containing the sum of the times needed to click an icon by all users, as
     * stored in the database,
     * and the [correctSumAll] corresponding to the sum of correctly clicked icons by all users, as
     * stored in the database.
     */
    data class CalculationValues(
        var timeSumUser: Double,
        var correctSumUser: Double,
        var timeSumAll: Double,
        var correctSumAll: Double
    ) {
        constructor() : this(0.0, 0.0, 0.0, 0.0)
    }

    /**
     * The values as used in the statistics relevant to the user.
     * The [timeUser] containing the average time the user needed to click one icon,
     * the [correctPercentUser] corresponding to the percentage of correct icons clicked by the user,
     * the [timeAll] containing the average time all participants of the study needed to click
     * one icon, as stored in the database,
     * and the [correctPercentAll] corresponding to the percentage of correct icons clicked by
     * all participants of the study, as stored in the database.
     * */
    data class StatisticsData(
        var timeUser: Double,
        var correctPercentUser: Double,
        var timeAll: Double,
        var correctPercentAll: Double
    ) {
        constructor() : this(0.0, 0.0, 0.0, 0.0)
    }
}