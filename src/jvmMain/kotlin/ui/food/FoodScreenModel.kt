package ui.food

import cafe.adriel.voyager.core.model.StateScreenModel
import common.launchOnIo
import data.api.ApiDataSource
import data.api.FoodModel
import kotlinx.coroutines.flow.update
import ui.food.FoodScreenModel.State

class FoodScreenModel(
    private val id: Long,
    private val apiDataSource: ApiDataSource = ApiDataSource(),
) : StateScreenModel<State>(State()) {

    init {
        fetchData()
    }

    fun fetchData() {
        launchOnIo {
            if (state.value.contentState == ContentState.Loading) return@launchOnIo
            mutableState.update { s -> s.copy(contentState = ContentState.Loading) }
            try {
                val result = apiDataSource.food(id)
                mutableState.update { s -> s.copy(contentState = ContentState.Result(result)) }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                mutableState.update { s -> s.copy(contentState = ContentState.Error) }
            }
        }
    }

    data class State(
        val contentState: ContentState = ContentState.Idle,
    )

    sealed class ContentState {
        object Idle : ContentState()
        object Loading : ContentState()
        data class Result(val model: FoodModel) : ContentState()
        object Error : ContentState()
    }
}