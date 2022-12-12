package ui.foodlist

import cafe.adriel.voyager.core.model.StateScreenModel
import common.launchOnIo
import data.api.ApiDataSource
import data.api.FoodModel
import kotlinx.coroutines.flow.update
import ui.foodlist.FoodListScreenModel.State

class FoodListScreenModel(
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
                val result = apiDataSource.foodList()
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
        data class Result(val foodList: List<FoodModel>) : ContentState()
        object Error : ContentState()
    }
}