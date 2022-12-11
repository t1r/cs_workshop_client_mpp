package data.auth

import io.ktor.client.plugins.auth.providers.*

object AuthDataSource {
    val currentAuthData: BasicAuthCredentials? = null

    val defaultAuthData = BasicAuthCredentials(username = "root", password = "root")
}