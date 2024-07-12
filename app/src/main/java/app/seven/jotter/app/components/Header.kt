package app.seven.jotter.app.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Header(title: String, icon: ImageVector) {
    Row {
        Text(text = title)
        Spacer(modifier = Modifier.fillMaxWidth())
        Icon(imageVector = icon, contentDescription = title)
    }
}