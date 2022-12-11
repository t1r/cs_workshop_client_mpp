import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import data.auth.AuthDataSource
import ui.auth.AuthScreen
import ui.foodlist.FoodListScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            Navigator(
                if (AuthDataSource.currentAuthData == null) AuthScreen
                else FoodListScreen
            )
        }
    }
}
