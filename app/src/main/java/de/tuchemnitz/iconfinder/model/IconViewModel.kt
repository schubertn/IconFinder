package de.tuchemnitz.iconfinder.model

import androidx.lifecycle.ViewModel
import de.tuchemnitz.iconfinder.R

/**
 * [IconViewModel] holds information about the study, including a list of all icons, the icons the
 * user already saw and data classes used to represent and save the collected data.
 * It also contains the needed methods to work with these informations.
 */
class IconViewModel : ViewModel() {

    /** will be used to not save data to db if study is done again
    // is true if the user already reached the ThankYouFragment once
    private var studyAlreadyDone = false

    fun getStudyAlreadyDone(): Boolean {
    return studyAlreadyDone
    }

    /**
     * Changes the value of [ studyAlreadyDone] to true to indicate that the user has already
     * done the study (reached the ThankYouFragment once).
    */
    fun setStudyAlreadyDone() {
    studyAlreadyDone = true
    }
     **/

    // the study consists of four phases
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

    // list of all colorful icons
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

    // list of all black and white icons
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

    // list of all icon names
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


    // list of numbers of already shown icons
    private val shownIconsList = mutableListOf<Int>()

    /**
     * Returns [shownIconsList] containing the numbers of the icons the user already saw.
     */
    fun getShownIcons(): MutableList<Int> {
        return shownIconsList
    }

    /**
     * Adds a new [numberOfIcon] to [shownIconsList].
     */
    fun addShownIcon(numberOfIcon: Int) {
        shownIconsList.add(numberOfIcon)
    }

    /**
     * Removes all elements from [shownIconsList]
     */
    fun clearShownIcons() {
        shownIconsList.clear()
    }


    // list that can be shuffled to contain all icons in random order
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


    // icon that was shown to user
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


    // id of the shown icon - needed for firestore
    private var shownIconId = 0
    fun getShownIconId(): Int{
        return shownIconId
    }
    fun setShownIconId(id: Int){
        shownIconId = id
    }


    data class FireStoreData(val phase: Int, val iconId: Int, val correctness: Boolean, val timeNeeded: Double)

    /**
     * Data as collected in the study. Every data-set consists of
     * the object [shownIcon] with the type [Icon], representing the icon the user saw,
     * the Boolean value [correctness] indicating whether the user clicked the right icon and
     * the Double [timeNeeded] containing the time the user needed to click the icon.
     */
    data class StudyData(val shownIcon: Icon, val correctness: Boolean, val timeNeeded: Double)

    // list of Study Data
    private val dataList = mutableListOf<StudyData>()

    /**
     * Returns a list containing [dataList].
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
     * Data as used in table with results in ResultFragment. Every data-set consists of
     * an Integer containing the [iconId] of the icon the user saw,
     * the String [correct] indicating whether the user clicked the right icon and
     * the String [time] representing the time the user needed to click the icon.
     */
    data class ResultData(val iconId: Int, val correct: String, val time: String)

    // list of Result Data
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
     * Deletes the collected data.
     * Clears [shownIconsList], [dataList] and [resultDataList].
     * Sets [shownIcon] back to its default value.
     */
    fun clearData() {
        shownIconsList.clear()
        shownIcon = Icon(0)
        dataList.clear()
        resultDataList.clear()
        phase = 1
    }
}
