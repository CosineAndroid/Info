package kr.cosine.info

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private companion object {
        val images = listOf(
            R.drawable.james_harden,
            R.drawable.kevin_durant,
            R.drawable.klay_thompson,
            R.drawable.lebron_james,
            R.drawable.stephen_curry
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        findViewById<ImageView>(R.id.profile_image).setImageResource(images.random())
        setText(R.id.info_id_description, "id")
        setText(R.id.info_name_description, "name")
    }

    private fun setText(id: Int, key: String) {
        val value = intent.getStringExtra(key)
        findViewById<TextView>(id).text = value
    }

    fun stop(view: View) {
        finish()
    }
}