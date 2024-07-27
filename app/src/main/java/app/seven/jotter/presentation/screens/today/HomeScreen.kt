package app.seven.jotter.presentation.screens.today

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.seven.jotter.presentation.screens.JotterTopAppBar
import app.seven.jotter.presentation.screens.appscaffold.appscaffold.viewmodel.AppNavigationAction
import app.seven.jotter.presentation.screens.taskdetails.TaskDetailsBottomSheet
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigationAction: (AppNavigationAction) -> Unit) {

    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        TaskDetailsBottomSheet(
            onClose = {
                showBottomSheet = false
            }
        )
    }

    Column {
        JotterTopAppBar(
            title = "Today",
            actions = {
                IconButton(onClick = {
                    onNavigationAction(AppNavigationAction.AddTaskScreen)
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Search")
                }
            },
            canNavigateBack = false,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            TodayDateArea(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            HorizontalDivider(
                thickness = .5.dp,
                color = Color.LightGray
            )
            TasksList(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                onClick = {
                    showBottomSheet = true
                })
        }
    }
}

@Composable
fun TodayDateArea(modifier: Modifier = Modifier) {
    val today = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("EE dd, MMMM"))

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = today,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        NextPrevButton()
    }
}

@Composable
fun NextPrevButton() {
    Row {
        Box(
            modifier = Modifier
                .background(Color.White)
                .border(
                    border = BorderStroke(
                        width = .3.dp,
                        color = Color.LightGray
                    ),
                    shape = RoundedCornerShape(
                        topStart = 4.dp,
                        bottomStart = 4.dp,
                    )
                )
                .padding(8.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Left")
        }
        Box(
            modifier = Modifier
                .background(Color.White)
                .border(
                    border = BorderStroke(
                        width = .3.dp,
                        color = Color.LightGray
                    ),
                    shape = RoundedCornerShape(
                        topEnd = 4.dp,
                        bottomEnd = 4.dp,
                    )
                )
                .padding(8.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Right")
        }
    }
}

//@Preview
//@Composable
//fun NextPrevButtonPreview() {
//    NextPrevButton()
//}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigationAction = {})
}
