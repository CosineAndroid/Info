package kr.cosine.info.enums

import kr.cosine.info.SignUpViewModel

enum class Typing(
    private val method: (SignUpViewModel, String) -> Unit
) {
    NAME(SignUpViewModel::setName),
    EMAIL(SignUpViewModel::setEmail),
    PASSWORD(SignUpViewModel::setPassword),
    PASSWORD_CONFIRM(SignUpViewModel::setPasswordConfirm);

    fun set(viewModel: SignUpViewModel, value: String) {
        method(viewModel, value)
    }
}