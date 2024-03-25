package kr.cosine.info.data

data class Account(
    val name: String,
    val id: String,
    val password: String
) {

    override fun toString(): String {
        return "$name, $id, $password"
    }
}