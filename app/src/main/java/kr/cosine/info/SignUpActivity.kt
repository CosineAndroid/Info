package kr.cosine.info

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kr.cosine.info.data.Account
import kr.cosine.info.data.Code
import kr.cosine.info.extension.getConstraintLayout
import kr.cosine.info.extension.getInput
import kr.cosine.info.extension.showToast
import kr.cosine.info.registry.AccountRegistry

class SignUpActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var idInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init() {
        nameInput = getConstraintLayout(R.id.name_group).findViewById(R.id.name_input)
        idInput = getConstraintLayout(R.id.id_group).findViewById(R.id.id_input)
        passwordInput = getConstraintLayout(R.id.password_group).findViewById(R.id.password_input)
    }

    fun signUp(view: View) {
        nameInput.getInput(this) getNameInput@ { name ->
            idInput.getInput(this) getIdInput@ { id ->
                if (AccountRegistry.isAccount(id)) {
                    showToast("이미 존재하는 아이디입니다.")
                    return@getIdInput
                }
                passwordInput.getInput(this) getPasswordInput@ { password ->
                    val account = Account(name, id, password)
                    AccountRegistry.addAccount(account)
                    showToast("회원가입이 완료되었습니다")
                    val intent = Intent(this, SignInActivity::class.java).apply {
                        putExtra("id", id)
                        putExtra("password", password)
                    }
                    setResult(Code.RESULT_CODE, intent)
                    finish()
                }
            }
        }
    }
}