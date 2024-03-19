package kr.cosine.info.registry

import kr.cosine.info.data.Account

object AccountRegistry {

    private val accounts = mutableListOf<Account>()

    fun isAccount(id: String): Boolean {
        return accounts.any { it.id == id }
    }

    fun addAccount(account: Account) {
        accounts.add(account)
    }

    fun findAccount(id: String, password: String): Account? {
        return accounts.find { it.id == id && it.password == password }
    }
}