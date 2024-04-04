package kr.cosine.info

import org.junit.Test

class SignUpViewModelTest {

    private val viewModel = SignUpViewModel()

    @Test
    fun view_model_test() {
        val isValid = viewModel.isValidPassword("qlalaa!^~a")
        println("비밀번호 맞는지: $isValid")
    }
}