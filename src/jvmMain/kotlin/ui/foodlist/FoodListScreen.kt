package ui.foodlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

object FoodListScreen : Screen {

    @Composable
    override fun Content() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {},
            content = {},
        )
    }
}