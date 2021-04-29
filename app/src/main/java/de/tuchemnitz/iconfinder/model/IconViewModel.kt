package de.tuchemnitz.iconfinder.model

import androidx.lifecycle.ViewModel
import de.tuchemnitz.iconfinder.R

class IconViewModel : ViewModel() {

    data class Icon(val imgId: Int)
    private val _allIcons = listOf(
            Icon(R.drawable.img_1),
            Icon(R.drawable.img_2),
            Icon(R.drawable.img_3),
            Icon(R.drawable.ic_launcher_foreground),
            Icon(R.drawable.ic_launcher_foreground),
            Icon(R.drawable.ic_launcher_foreground),
            Icon(R.drawable.ic_launcher_foreground),
            Icon(R.drawable.ic_launcher_foreground),
            Icon(R.drawable.ic_launcher_foreground)
    )
    val allIcons = _allIcons

    private val _shownIcons = mutableListOf<Int>()
    val shownIcons = _shownIcons
    fun addShownIcon(numberOfIcon: Int) {
        _shownIcons.add(numberOfIcon)
    }


    /**
    // this is not correct (clicked icon would have to be allIconsRandomized[button number]
    // also not sure about _clickedIcon type
    // also not used yet in any way
    private var _clickedIcon = Icon(0)
    val clickedIcon = _clickedIcon

    fun onIconClicked(buttonNumber: Int) {
        _clickedIcon = allIcons [ buttonNumber ]
    }
    **/

}