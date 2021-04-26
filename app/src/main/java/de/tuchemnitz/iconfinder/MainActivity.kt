package de.tuchemnitz.iconfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // setupActionBarWithNavController(navController)


       // setContentView(R.layout.activity_main)
        TODO("Make return button on phone work properly (Disable/return to WelcomeFragment")
        TODO("Make OneIconFragment nicer")


    }
}

    /**
    val rnd = (1..3).random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// setContentView(R.layout.activity_main)
        setContentView(R.layout.one_icon_fragment)
        val img = findViewById<View>(R.id.imageView) as ImageView
        val str = "img_" + rnd
        img.setImageDrawable(resources.getDrawable(getResourceID(str, "drawable", applicationContext)))


    }
**/
/**
 * class MainActivity : AppCompatActivity(R.layout.activity_main) {

override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)

val navHostFragment = supportFragmentManager
.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
val navController = navHostFragment.navController

setupActionBarWithNavController(navController)
}
}
 */


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