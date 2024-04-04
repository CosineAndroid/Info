package kr.cosine.info

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    private val passwordRegex = Regex("^.*(?=^.{10,}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@^*~]).*\$")

    private val _nameEvent: MutableLiveData<String> = MutableLiveData()
    val nameEvent: LiveData<String> get() = _nameEvent

    private val _emailEvent: MutableLiveData<String> = MutableLiveData()
    val emailEvent: LiveData<String> get() = _emailEvent

    private val _passwordEvent: MutableLiveData<String> = MutableLiveData()
    val passwordEvent: LiveData<String> get() = _passwordEvent

    private val _passwordConfirmEvent: MutableLiveData<String> = MutableLiveData()
    val passwordConfirmEvent: LiveData<String> get() = _passwordConfirmEvent

    fun setName(name: String) {
        _nameEvent.value = name
    }

    fun setEmail(email: String) {
        _emailEvent.value = email
    }

    fun setPassword(password: String) {
        _passwordEvent.value = password
    }

    fun setPasswordConfirm(passwordConfirm: String) {
        _passwordConfirmEvent.value = passwordConfirm
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.matches(passwordRegex)
    }
}