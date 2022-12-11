package ui.foodlist

import cafe.adriel.voyager.core.model.StateScreenModel
import data.api.ApiDataSource
import ui.foodlist.FoodListScreenModel.State

class FoodListScreenModel(
    private val apiDataSource: ApiDataSource = ApiDataSource(),
) : StateScreenModel<State>(State()) {


    data class State(
        val contentState: ContentState = ContentState.Idle,
    )

    sealed class ContentState {
        object Idle : ContentState()
        object Loading : ContentState()
        data class Result(val model: Any) : ContentState()
    }
}