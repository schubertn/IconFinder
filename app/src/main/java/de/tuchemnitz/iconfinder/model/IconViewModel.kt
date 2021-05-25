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
     * Every icon is identified by an unique Integer [imgId].
     */
    data class Icon(val imgId: Int)

    /**
     * List of all colorful icons. The order they are in corresponds to their ids.
     */
    private val colorIcons = listOf(
        Icon(R.drawable.icon_0), // id 0
        Icon(R.drawable.icon_1), // id 1
        Icon(R.drawable.icon_2), // id 2
        Icon(R.drawable.icon_3), // id 3
        Icon(R.drawable.icon_4), // id 4
        Icon(R.drawable.icon_5), // id 5
        Icon(R.drawable.icon_6), // id 6
        Icon(R.drawable.icon_7), // id 7
        Icon(R.drawable.icon_8)  // id 8
    )

    /**
     * Returns a list [colorIcons] of all colorful icons used in this study.
     */
    fun getColorIcons(): List<Icon> {
        return colorIcons
    }

    /**
     * List of all black and white icons. The order they are in corresponds to their ids.
     */
    private val blackWhiteIcons = listOf(
        Icon(R.drawable.icon_0_bw),
        Icon(R.drawable.icon_1_bw),
        Icon(R.drawable.icon_2_bw),
        Icon(R.drawable.icon_3_bw),
        Icon(R.drawable.icon_4_bw),
        Icon(R.drawable.icon_5_bw),
        Icon(R.drawable.icon_6_bw),
        Icon(R.drawable.icon_7_bw),
        Icon(R.drawable.icon_8_bw)
    )

    /**
     * Returns a list [blackWhiteIcons] of all black and white icons used in this study.
     */
    fun getBlackWhiteIcons(): List<Icon> {
        return blackWhiteIcons
    }


    /**
     * List of all icon names. The order they are in corresponds to their ids.
     */
    private val iconNames = listOf(
        "Art",
        "Browser",
        "Wetter",
        "Einstellungen",
        "Galerie",
        "Mail",
        "Maps",
        "Nachrichten",
        "Telefon"
    )

    /**
     * Returns a list [iconNames] of all names of the icons used in this study.
     */
    fun getIconNames(): List<String> {
        return iconNames
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
     * Icon that was shown to the user.
     */
    private var shownIcon = Icon(0)

    /**
     * Returns an object of the type [Icon], containing the icon the user last saw.
     */
    fun getShownIcon(): Icon {
        return shownIcon
    }

    /**
     * Sets the value of the [icon] the user just saw.
     */
    fun setShownIcon(icon: Icon) {
        shownIcon = icon
    }


    /**
     * Integer id of the shown icon. The icons have the ids 0 to 8.
     */
    private var shownIconId = 0

    /**
     * Returns the [shownIconId] of the icon the user last saw.
     */
    fun getShownIconId(): Int{
        return shownIconId
    }

    /**
     * Sets the [id] of the icon the user last saw
     */
    fun setShownIconId(id: Int){
        shownIconId = id
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
        shownIcon = Icon(0)
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
}
