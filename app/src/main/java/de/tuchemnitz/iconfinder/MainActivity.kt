package de.tuchemnitz.iconfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

// This makes random Images appear
/**
class MainActivity : AppCompatActivity() {
val rnd = (1..3).random()

override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
// setContentView(R.layout.activity_main)
setContentView(R.layout.one_icon_fragment)

val img = findViewById<View>(R.id.imgRandom) as ImageView
val str = "img_" + rnd
img.setImageDrawable(resources.getDrawable(getResourceID(str, "drawable", applicationContext)))


}

private fun getResourceID(
resName: String,
resType: String?,
ctx: Context
): Int {
val ResourceID: Int = ctx.getResources().getIdentifier(
resName, resType,
ctx.getApplicationInfo().packageName
)
return if (ResourceID == 0) {
throw IllegalArgumentException(
"No resource string found with name $resName"
)
} else {
ResourceID
}
}

}
 **/