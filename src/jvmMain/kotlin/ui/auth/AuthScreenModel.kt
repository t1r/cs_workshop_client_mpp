package ui.auth

import cafe.adriel.voyager.core.model.StateScreenModel
import common.launchOnIo
import data.api.ApiDataSource
import data.auth.AuthDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import ui.auth.AuthScreenModel.State

class AuthScreenModel(
    private val apiDataSource: ApiDataSource = ApiDataSource(),
    private val authDataSource: AuthDataSource = AuthDataSource,
) : StateScreenModel<State>(State()) {
    private val mutableLabels: MutableSharedFlow<Label> = MutableSharedFlow()
    val labels: SharedFlow<Label> = mutableLabels.asSharedFlow()

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
        mutableState.update { s ->
            s.copy(
                name = AuthDataSource.defaultAuthData.username,
                password = AuthDataSource.defaultAuthData.password,
            )
        }
    }

    fun auth() {
        launchOnIo {
            val name = state.value.name
            val password = state.value.password
            if (name.isNullOrBlank() || password.isNullOrBlank()) return@launchOnIo
            if (state.value.isAuthInProgress) return@launchOnIo
            mutableState.update { s ->
                s.copy(
                    isAuthInProgress = true,
                    isError = false,
                )
            }
            try {
                apiDataSource.auth(
                    name = name,
                    password = password,
                )
                authDataSource.update(
                    username = name,
                    password = password,
                )
                mutableState.update { s -> s.copy(isAuthInProgress = false) }
                mutableLabels.emit(Label.GoToFoods)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                mutableState.update { s ->
                    s.copy(
                        isAuthInProgress = false,
                        isError = true,
                    )
                }
            }
        }
    }

    data class State(
        val name: String? = AuthDataSource.currentAuthData()?.username,
        val password: String? = AuthDataSource.currentAuthData()?.password,
        val isAuthInProgress: Boolean = false,
        val isError: Boolean = false,
    )

    sealed class Label {
        object GoToFoods : Label()
    }
}