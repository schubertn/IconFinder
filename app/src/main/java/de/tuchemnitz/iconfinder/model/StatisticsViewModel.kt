package de.tuchemnitz.iconfinder.model

import androidx.lifecycle.ViewModel

class StatisticsViewModel:ViewModel() {

    data class CalculationValues(
        var timeSumUser: Double,
        var correctSumUser: Double,
        var timeSumAll: Double,
        var correctSumAll: Double
    ) {
        constructor() : this(0.0, 0.0, 0.0, 0.0)
    }

     var generalCalcValues = CalculationValues()
     /**
    fun setGeneralCalcValues(timeSumUser: Double, correctSumUser: Double, timeSumAll: Double, correctSumAll: Double) {
        generalCalcValues.timeSumUser = timeSumUser
        generalCalcValues.timeSumAll = timeSumAll
        generalCalcValues.correctSumUser = correctSumUser
        generalCalcValues.correctSumAll = correctSumAll
    }

    fun getGeneralCalcValues() : CalculationValues{
        return generalCalcValues
    }
**/
     var phaseOneCalcValues = CalculationValues()
/**
    fun setPhaseOneCalcValues(timeSumUser: Double, correctSumUser: Double, timeSumAll: Double, correctSumAll: Double) {
        phaseOneCalcValues.timeSumUser = timeSumUser
        phaseOneCalcValues.timeSumAll = timeSumAll
        phaseOneCalcValues.correctSumUser = correctSumUser
        phaseOneCalcValues.correctSumAll = correctSumAll
    }

    fun getPhaseOneCalcValues() : CalculationValues{
        return phaseOneCalcValues
    }
**/
     var phaseTwoCalcValues = CalculationValues()
/**
    fun setPhaseTwoCalcValues(timeSumUser: Double, correctSumUser: Double, timeSumAll: Double, correctSumAll: Double) {
        phaseTwoCalcValues.timeSumUser = timeSumUser
        phaseTwoCalcValues.timeSumAll = timeSumAll
        phaseTwoCalcValues.correctSumUser = correctSumUser
        phaseTwoCalcValues.correctSumAll = correctSumAll
    }

    fun getPhaseTwoCalcValues() : CalculationValues{
        return phaseTwoCalcValues
    }
    **/

     var phaseThreeCalcValues = CalculationValues()

    /**
    fun setPhaseThreeCalcValues(timeSumUser: Double, correctSumUser: Double, timeSumAll: Double, correctSumAll: Double) {
        phaseThreeCalcValues.timeSumUser = timeSumUser
        phaseThreeCalcValues.timeSumAll = timeSumAll
        phaseThreeCalcValues.correctSumUser = correctSumUser
        phaseThreeCalcValues.correctSumAll = correctSumAll
    }

    fun getPhaseThreeCalcValues() : CalculationValues{
        return phaseThreeCalcValues
    }
    **/

    var phaseFourCalcValues = CalculationValues()

    /**
    fun setPhaseFourCalcValues(timeSumUser: Double, correctSumUser: Double, timeSumAll: Double, correctSumAll: Double) {
        phaseFourCalcValues.timeSumUser = timeSumUser
        phaseFourCalcValues.timeSumAll = timeSumAll
        phaseFourCalcValues.correctSumUser = correctSumUser
        phaseFourCalcValues.correctSumAll = correctSumAll
    }

    fun getPhaseFourCalcValues() : CalculationValues{
        return phaseFourCalcValues
    }
    **/





    data class StatisticsData(
        var timeUser: Double,
        var correctPercentageUser: Double,
        var timeAll: Double,
        var correctPercentageAll: Double
    ) {
        constructor() : this(0.0, 0.0, 0.0, 0.0)
    }

    private var generalStatistics = StatisticsData()
    fun setGeneralStatistics(timeUser: Double, correctPercentageUser: Double, timeAll: Double, correctPercentageAll: Double) {
        generalStatistics.timeUser = timeUser
        generalStatistics.correctPercentageUser = correctPercentageUser
        generalStatistics.timeAll = timeAll
        generalStatistics.correctPercentageAll = correctPercentageAll

    }

    fun getGeneralStatistics(): StatisticsData {
        return generalStatistics
    }


    private var phaseOneStatistics = StatisticsData()
    fun setPhaseOneStatistics(timeUser: Double, correctPercentageUser: Double, timeAll: Double, correctPercentageAll: Double){
        phaseOneStatistics.timeUser = timeUser
        phaseOneStatistics.correctPercentageUser = correctPercentageUser
        phaseOneStatistics.timeAll = timeAll
        phaseOneStatistics.correctPercentageAll = correctPercentageAll
    }

    fun getPhaseOneStatistics(): StatisticsData{
        return phaseOneStatistics
    }

    private var phaseTwoStatistics = StatisticsData()
    fun setPhaseTwoStatistics(timeUser: Double, correctPercentageUser: Double, timeAll: Double, correctPercentageAll: Double){
        phaseTwoStatistics.timeUser = timeUser
        phaseTwoStatistics.correctPercentageUser = correctPercentageUser
        phaseTwoStatistics.timeAll = timeAll
        phaseTwoStatistics.correctPercentageAll = correctPercentageAll
    }

    fun getPhaseTwoStatistics(): StatisticsData{
        return phaseTwoStatistics
    }

    private var phaseThreeStatistics = StatisticsData()
    fun setPhaseThreeStatistics(timeUser: Double, correctPercentageUser: Double, timeAll: Double, correctPercentageAll: Double){
        phaseThreeStatistics.timeUser = timeUser
        phaseThreeStatistics.correctPercentageUser = correctPercentageUser
        phaseThreeStatistics.timeAll = timeAll
        phaseThreeStatistics.correctPercentageAll = correctPercentageAll
    }

    fun getPhaseThreeStatistics(): StatisticsData{
        return phaseThreeStatistics
    }

    private var phaseFourStatistics = StatisticsData()
    fun setPhaseFourStatistics(timeUser: Double, correctPercentageUser: Double, timeAll: Double, correctPercentageAll: Double){
        phaseFourStatistics.timeUser = timeUser
        phaseFourStatistics.correctPercentageUser = correctPercentageUser
        phaseFourStatistics.timeAll = timeAll
        phaseFourStatistics.correctPercentageAll = correctPercentageAll
    }

    fun getPhaseFourStatistics(): StatisticsData{
        return phaseFourStatistics
    }


}