package app.seven.jotter.presentation.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> ObserveFlowStateAsEvents(
    flow: Flow<T>,
    onEvent: (T) -> Unit
) {
    /**
     * LifecycleEventEffect(Lifecycle.Event.ON_RESUME){
     *         LocalLifecycleOwner.current.lifecycleScope.launch {  }
     *         Log.d("JOTTER_NAV_GRAPH", "onResume")
     *     }
     */
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate){
                flow.collect(onEvent)
            }
        }
    }
}