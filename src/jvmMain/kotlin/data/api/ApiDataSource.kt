package data.api

import data.auth.AuthDataSource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiDataSource {
    private val client = HttpClient(CIO) {
        install(Auth) {
            basic {
                credentials { AuthDataSource.currentAuthData() }
                realm = "Access to the '/' path"
            }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
            filter { request ->
                request.url.host.contains("ktor.io")
            }
        }
    }

    suspend fun foodList(): List<FoodModel> {
        return client.get("${BASE_URL}/api/foodList").body()
    }

    suspend fun auth(
        name: String,
        password: String,
    ) {
        client.submitForm(
            "${BASE_URL}/api/auth",
            formParameters = Parameters.build {
                append("name", name)
                append("password", password)
            }
        )
    }

    companion object {
        private const val BASE_URL = "http://0.0.0.0:8080"
    }
}