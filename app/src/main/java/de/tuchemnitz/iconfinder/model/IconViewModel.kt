package de.tuchemnitz.iconfinder.model

import androidx.lifecycle.ViewModel
import de.tuchemnitz.iconfinder.R

class IconViewModel : ViewModel() {

    data class Icon(val imgId: Int)
    val allIcons = listOf(
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

    val shownIcons = mutableListOf<Int>()
    fun addShownIcon(numberOfIcon: Int) {
        shownIcons.add(numberOfIcon)
    }
}