package ui.addfood

import cafe.adriel.voyager.core.model.StateScreenModel
import common.launchOnIo
import data.api.ApiDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import ui.addfood.AddFoodScreenModel.State

class AddFoodScreenModel(
    private val apiDataSource: ApiDataSource = ApiDataSource(),
) : StateScreenModel<State>(State()) {
    private val mutableLabels: MutableSharedFlow<Label> = MutableSharedFlow()
    val labels: SharedFlow<Label> = mutableLabels.asSharedFlow()

    fun onNameChanged(value: String?) {
        mutableState.update { state ->
            state.copy(name = value)
        }
    }

    fun onWeightChanged(value: String?) {
        mutableState.update { state ->
            state.copy(weight = value)
        }
    }

    fun addFood() {
        launchOnIo {
            val name = state.value.name
            if (name.isNullOrBlank()) return@launchOnIo
            val weight = state.value.weight?.toFloatOrNull() ?: return@launchOnIo
            if (state.value.isAddFoodInProgress) return@launchOnIo
            mutableState.update { s ->
                s.copy(
                    isAddFoodInProgress = true,
                    isError = false,
                )
            }
            try {
                apiDataSource.addFood(
                    name = name,
                    weight = weight,
                )
                mutableState.update { s -> s.copy(isAddFoodInProgress = false) }
                mutableLabels.emit(Label.Back)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                mutableState.update { s ->
                    s.copy(
                        isAddFoodInProgress = false,
                        isError = true,
                    )
                }
            }
        }
    }

    data class State(
        val name: String? = null,
        val weight: String? = null,
        val isAddFoodInProgress: Boolean = false,
        val isError: Boolean = false,
    )

    sealed class Label {
        object Back : Label()
    }
}