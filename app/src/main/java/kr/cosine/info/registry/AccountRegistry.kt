package kr.cosine.info.registry

import kr.cosine.info.data.Account

object AccountRegistry {

    private val accounts = mutableListOf<Account>()

    fun isAccount(email: String): Boolean {
        return accounts.any { it.email == email }
    }

    fun addAccount(account: Account) {
        accounts.add(account)
    }

    fun findAccount(email: String, password: String): Account? {
        return accounts.find { it.email == email && it.password == password }
    }
}