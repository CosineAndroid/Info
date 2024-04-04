package kr.cosine.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kr.cosine.info.data.Account
import kr.cosine.info.databinding.ActivitySignInBinding
import kr.cosine.info.extension.showToast
import kr.cosine.info.intent.IntentKey
import kr.cosine.info.registry.AccountRegistry

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val intent = result.data ?: return@registerForActivityResult
        val account = intent.getSerializableExtra(IntentKey.ACCOUNT) as Account
        emailInput.setText(account.email)
        passwordInput.setText(account.password)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding) {
        emailInput = emailGroup.emailInput
        passwordInput = passwordGroup.passwordInput
    }

    fun login(view: View) {
        emailInput.getInput(this) emailInput@ { email ->
            passwordInput.getInput(this) passwordInput@ { password ->
                val account = AccountRegistry.findAccount(email, password) ?: run {
                    showToast("존재하지 않는 계정입니다.")
                    return@passwordInput
                }
                showToast("로그인 성공")
                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra(IntentKey.ACCOUNT, account)
                }
                startActivity(intent)
            }
        }
    }

    fun showSignUpActivity(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun EditText.getInput(context: Context, textScope: (String) -> Unit) {
        if (text.isEmpty()) {
            context.showToast(hint.toString())
            return
        }
        textScope(text.toString())
    }
}