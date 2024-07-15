package app.seven.jotter.presentation.screens.taskeditorscreen.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.seven.jotter.presentation.components.ActionEditorDialog
import app.seven.jotter.presentation.screens.taskeditorscreen.component.IconHelper
import app.seven.jotter.presentation.theme.spacing
import app.seven.jotter.common.extensions.titleCase
import app.seven.jotter.core.models.TaskCategory

@Composable
fun TaskCategoryDialog(
    categories: List<TaskCategory>,
    onCancel: () -> Unit,
    onChangeCategory: (TaskCategory) -> Unit,
) {

    val selectedCategory = rememberSaveable { mutableStateOf(categories.first()) }

    ActionEditorDialog(
        title = "Select Category",
        onCancel = onCancel,
        onConfirm = {
            onChangeCategory(selectedCategory.value)
        }) {

        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 130.dp)) {
            items(categories.size) { index ->
                val category = categories[index]

                Card(
                    modifier = Modifier.padding(spacing().xSmall),
                    onClick = { selectedCategory.value = category },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                        enabled = true,
                        borderWidth = if (selectedCategory.value != category) .3.dp else .5.dp,
                        borderColor = if (selectedCategory.value != category)
                            Color.LightGray
                        else MaterialTheme.colorScheme.primary
                    ),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize().padding(spacing().small),
                    ) {
                        IconHelper.GetCategoryIcon(category)
                        Spacer(modifier = Modifier.padding(spacing().xSmall))
                        Text(
                            category.name.titleCase(),
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun TaskCategoryDialogPreview() {
    TaskCategoryDialog(
        categories = TaskCategory.entries,
        onCancel = { /*TODO*/ }) {
    }
}