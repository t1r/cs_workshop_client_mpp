package ui.auth

import cafe.adriel.voyager.core.model.StateScreenModel
import common.launchOnIo
import data.api.ApiDataSource
import data.auth.AuthDataSource
import kotlinx.coroutines.flow.update
import ui.auth.AuthScreenModel.State

class AuthScreenModel(
    private val apiDataSource: ApiDataSource = ApiDataSource(),
) : StateScreenModel<State>(State()) {

    fun onNameChanged(value: String?) {
        mutableState.update { state ->
            state.copy(name = value)
        }
    }

    fun onPasswordChanged(value: String?) {
        mutableState.update { state ->
            state.copy(password = value)
        }
    }

    fun setDefault() {
        mutableState.update { state ->
            state.copy(
                name = AuthDataSource.defaultAuthData.username,
                password = AuthDataSource.defaultAuthData.password,
            )
        }
    }

    fun auth() {
        launchOnIo {
            val name = state.value.name ?: return@launchOnIo
            val password = state.value.password ?: return@launchOnIo
            apiDataSource.auth(
                name = name,
                password = password,
            )
        }
    }

    data class State(
        val name: String? = AuthDataSource.currentAuthData?.username,
        val password: String? = AuthDataSource.currentAuthData?.password,
    )
}