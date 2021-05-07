package de.tuchemnitz.iconfinder.model

import androidx.lifecycle.ViewModel
import de.tuchemnitz.iconfinder.R

class IconViewModel : ViewModel() {

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
     *
     */
    fun getAllIcons(): List<Icon> {
        return allIcons
    }

    // list of numbers of already shown icons
    private val shownIconsList = mutableListOf<Int>()

    fun getShownIcons(): MutableList<Int> {
        return shownIconsList
    }

    fun addShownIcon(numberOfIcon: Int) {
        shownIconsList.add(numberOfIcon)
    }


    // list that can be shuffled to contain all icons in random order
    private var shuffleList = mutableListOf<Icon>()

    fun setShuffleList(shuffledList: MutableList<Icon>) {
        shuffleList = shuffledList
    }

    fun getShuffleList(): MutableList<Icon> {
        return shuffleList
    }

    // icon that was shown to user
    private var shownIcon = Icon(0)

    fun setShownIcon(icon: Icon) {
        shownIcon = icon
    }

    fun getShownIcon(): Icon {
        return shownIcon
    }

    // collect the data for study
    // shownIcon: the icon the user saw
    // correctness: if the user clicked the right icon
    // timeNeeded: time until the user clicked an icon
    /**
     * Data as collected in the study. Every data-set consists of
     * the object [shownIcon] with the type [Icon], representing the icon the user saw,
     * the Boolean value [correctness] indicating whether the user clicked the right icon and
     * the Double [timeNeeded] containing the time the user needed to click the icon.
     */
    data class StudyData(val shownIcon: Icon, val correctness: Boolean, val timeNeeded: Double)

    private val dataList = mutableListOf<StudyData>()

    /**
     *
     */
    fun addData(data: StudyData) {
        dataList.add(data)
    }

    fun getData(): MutableList<StudyData> {
        return dataList
    }

    /**
     * Data as used in table with results in ResultFragment. Every data-set consists of
     * an Integer containing the [iconId] of the icon the user saw,
     * the String [correct] indicating whether the user clicked the right icon and
     * the String [time] representing the time the user needed to click the icon.
     */
    data class ResultData(val iconId: Int, val correct: String, val time: String)

    private val resultDataList = mutableListOf<ResultData>()

    /**
     * Adds new a [data] object to [resultDataList].
     */
    fun addResultData(data: ResultData) {
        resultDataList.add(data)
    }

    /**
     * Returns a list containing [ResultData]. Used in table with results.
     */
    fun getResultData(): MutableList<ResultData> {
        return resultDataList
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
