package common

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.*

fun ScreenModel.launchOnIo(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return coroutineScope.launch {
        withContext(Dispatchers.IO) {
            block()
        }
    }
}