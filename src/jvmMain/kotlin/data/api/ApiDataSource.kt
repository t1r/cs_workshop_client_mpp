package data.api

import data.auth.AuthDataSource
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiDataSource {
    private val client = HttpClient(CIO) {
        install(Auth) {
            basic {
                credentials { AuthDataSource.currentAuthData }
                realm = "Access to the '/' path"
            }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun foodList() {
        val response: HttpResponse = client.get("${BASE_URL}/api/foodList")
        println(response.bodyAsText())
    }

    suspend fun auth(
        name: String,
        password: String,
    ) {
//        val response: HttpResponse = client.post("${BASE_URL}/api/auth") {
        val response: HttpResponse = client.submitForm(
            "${BASE_URL}/api/auth",
            formParameters = Parameters.build {
                append("name", name)
                append("password", password)
            }
        )
//            contentType(ContentType.Application.Json)
//            setParameters {
//                put("phone", phone)
//            }
//            this.
//            formParameters = Parameters.build {
//                append("username", "JetBrains")
//                append("email", "example@jetbrains.com")
//                append("password", "foobar")
//                append("confirmation", "foobar")
//            }
        println(response.bodyAsText())
    }

    companion object {
        private const val BASE_URL = "http://0.0.0.0:8080"
    }
}