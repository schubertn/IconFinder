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
     * Every icon is identified by an unique Integer [imgId].
     */
    data class Icon(val imgId: Int)

    // list of all icons
    private val allIcons = listOf(
            Icon(R.drawable.icon_0),
            Icon(R.drawable.icon_1),
            Icon(R.drawable.icon_2),
            Icon(R.drawable.icon_3),
            Icon(R.drawable.icon_4),
            Icon(R.drawable.icon_5),
            Icon(R.drawable.icon_6),
            Icon(R.drawable.icon_7),
            Icon(R.drawable.icon_8)
    )

    /**
     * Returns a list [allIcons] of all icons used in this study.
     */
    fun getAllIcons(): List<Icon> {
        return allIcons
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
    }
}
