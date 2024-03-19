package kr.cosine.info

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kr.cosine.info.extension.getConstraintLayout
import kr.cosine.info.extension.getInput
import kr.cosine.info.extension.showToast
import kr.cosine.info.registry.AccountRegistry

class SignInActivity : AppCompatActivity() {

    private lateinit var idInput: EditText
    private lateinit var passwordInput: EditText

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val intent = result.data ?: return@registerForActivityResult
        intent.getStringExtra("id")?.let { idInput.setText(it) }
        intent.getStringExtra("password")?.let { passwordInput.setText(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init()
    }

    private fun init() {
        idInput = getConstraintLayout(R.id.id_group).findViewById(R.id.id_input)
        passwordInput = getConstraintLayout(R.id.password_group).findViewById(R.id.password_input)
    }

    fun login(view: View) {
        idInput.getInput(this) idInput@ { id ->
            passwordInput.getInput(this) passwordInput@ { password ->
                val account = AccountRegistry.findAccount(id, password) ?: run {
                    showToast("존재하지 않는 계정입니다.")
                    return@passwordInput
                }
                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("id", id)
                    putExtra("name", account.name)
                }
                startActivity(intent)
            }
        }
    }

    fun showSignUpActivity(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        resultLauncher.launch(intent)
    }
}