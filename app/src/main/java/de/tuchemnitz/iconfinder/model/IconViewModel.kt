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

    fun clearShownIconsList() {
        shownIconsList.clear()
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
    data class StudyData(val shownIcon: Icon, val correctness: Boolean, val timeNeeded: Double)

    private val dataList = mutableListOf<StudyData>()

    fun addData(data: StudyData) {
        dataList.add(data)
    }

    fun getData(): MutableList<StudyData> {
        return dataList
    }

    val test = "testest"


    data class StringData(val icon: String, val correct: String, val time: String)
    var dataTest = StringData(Icon(R.drawable.icon_0).imgId.toString(), true.toString(), 1.00.toString())

    val datatest2 = StudyData(Icon(R.drawable.icon_0), true, 1.00)

    data class ResultData(val icon: Int, val correct: String, val time: String)
    private val resultData = mutableListOf<ResultData>()
    fun addResultData(data: ResultData) {
        resultData.add(data)
    }
    fun getResultData(): MutableList<ResultData> {
        return resultData
    }

}
