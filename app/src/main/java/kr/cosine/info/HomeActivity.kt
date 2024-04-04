package kr.cosine.info

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kr.cosine.info.data.Account
import kr.cosine.info.databinding.ActivityHomeBinding
import kr.cosine.info.intent.IntentKey

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

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding) {
        findViewById<ImageView>(R.id.profile_image).setImageResource(images.random())
        val account = intent.getSerializableExtra(IntentKey.ACCOUNT) as Account
        infoEmailDescription.text = account.email
        infoNameDescription.text = account.name
    }

    fun stop(view: View) {
        finish()
    }
}