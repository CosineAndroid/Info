package kr.cosine.info

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import kr.cosine.info.data.Account
import kr.cosine.info.data.Code
import kr.cosine.info.databinding.ActivitySignUpBinding
import kr.cosine.info.databinding.InputGroupBinding
import kr.cosine.info.enums.Typing
import kr.cosine.info.extension.showToast
import kr.cosine.info.intent.IntentKey
import kr.cosine.info.registry.AccountRegistry

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this)[SignUpViewModel::class.java]
    }

    private var inputMap = emptyMap<Typing, InputGroupBinding>()

    private val signUpButton by lazy { binding.signUpButton }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        initViewModelEvent()
    }

    private fun init() = with(binding) {
        inputMap = mapOf(
            Typing.NAME to nameGroup.apply {
                title.text = "이름"
                error.text = "이름을 입력해주세요."
            },
            Typing.EMAIL to emailGroup.apply {
                title.text = "이메일"
                error.text = "이메일을 입력해주세요."
            },
            Typing.PASSWORD to passwordGroup.apply {
                title.text = "비밀번호"
                error.text = "10자리 이상, 숫자 & 특수문자 포함"
                input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            },
            Typing.PASSWORD_CONFIRM to passwordConfirmGroup.apply {
                title.text = "비밀번호 확인"
                error.text = "비밀번호를 다시 입력해주세요."
                input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        )
        inputMap.forEach { (inputType, group) ->
            val inputEditText = group.input
            val apply = {
                val inputText = inputEditText.text.toString()
                inputType.set(viewModel, inputText)
            }
            inputEditText.addTextChangedListener {
                apply()
            }
            inputEditText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) return@setOnFocusChangeListener
                apply()
            }
        }
        signUpButton.isEnabled = false
    }

    fun signUp(view: View) {
        val name = viewModel.nameEvent.value ?: return
        val email = viewModel.emailEvent.value ?: return
        val password = viewModel.passwordEvent.value ?: return
        if (AccountRegistry.isAccount(email)) {
            showToast("이미 존재하는 이메일입니다.")
            return
        }
        val account = Account(name, email, password)
        AccountRegistry.addAccount(account)
        val intent = Intent(this, SignInActivity::class.java).apply {
            putExtra(IntentKey.ACCOUNT, account)
        }
        setResult(Code.RESULT_CODE, intent)
        finish()
    }

    private fun initViewModelEvent() {
        initNameEvent()
        initEmailEvent()
        initPasswordEvent()
        initPasswordConfirmEvent()
    }

    private fun initNameEvent() {
        viewModel.nameEvent.observe(this) { name ->
            showOrHideError(Typing.NAME, name.isNotBlank())
        }
    }

    private fun initEmailEvent() {
        viewModel.emailEvent.observe(this) { email ->
            showOrHideError(Typing.EMAIL, viewModel.isValidEmail(email) && !email.containsSpace())
        }
    }

    private fun initPasswordEvent() {
        viewModel.passwordEvent.observe(this) { password ->
            showOrHideError(Typing.PASSWORD, viewModel.isValidPassword(password) && !password.containsSpace())
        }
    }

    private fun initPasswordConfirmEvent() {
        viewModel.passwordConfirmEvent.observe(this) { passwordConfirm ->
            val password = viewModel.passwordEvent.value ?: return@observe
            val isValid = password.isNotBlank() && passwordConfirm.isNotBlank() && password == passwordConfirm
            showOrHideError(Typing.PASSWORD_CONFIRM, isValid)
        }
    }

    private fun showOrHideError(typing: Typing, isValid: Boolean) {
        inputMap[typing]!!.error.visibility = if (isValid) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
        switchSignUpStatus()
    }

    private fun switchSignUpStatus() {
        signUpButton.isEnabled = inputMap.all { it.value.error.visibility == View.INVISIBLE }
    }

    private fun String.containsSpace(): Boolean = contains(" ")
}