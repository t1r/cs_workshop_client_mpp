package data.auth

import io.ktor.client.plugins.auth.providers.*

object AuthDataSource {
    private var currentAuthData: BasicAuthCredentials? = null
    fun currentAuthData(): BasicAuthCredentials? = currentAuthData

    val defaultAuthData = BasicAuthCredentials(username = "root", password = "root")

    fun update(
        username: String,
        password: String,
    ) {
        currentAuthData = BasicAuthCredentials(username = username, password = password)
    }
}