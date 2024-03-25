package kr.cosine.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.cosine.info.data.Account

class SignUpViewModel : ViewModel() {

    private val _accountEvent: MutableLiveData<Account> = MutableLiveData()
    val accountEvent: LiveData<Account> get() = _accountEvent

    fun setAccount(account: Account) {
        _accountEvent.value = account
    }
}