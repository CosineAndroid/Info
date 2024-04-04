package kr.cosine.info.data

import java.io.Serializable

class Account(
    val name: String,
    val email: String,
    val password: String
) : Serializable {

    override fun toString(): String = "$name, $email, $password"
}