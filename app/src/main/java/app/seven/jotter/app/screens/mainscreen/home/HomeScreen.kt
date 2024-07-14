package app.seven.jotter.app.screens.mainscreen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.seven.jotter.app.screens.JotterTopAppBar
import app.seven.jotter.app.screens.mainscreen.appscaffold.viewmodel.AppNavigationAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAppNavigationAction: (AppNavigationAction) -> Unit) {
    Column {
        JotterTopAppBar(
            title = "Today",
            actions = {
                IconButton(onClick = {
                    onAppNavigationAction(AppNavigationAction.AddTask)
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Search")
                }
            },
            canNavigateBack = false,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            DateArea()
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            TasksList(modifier = Modifier.fillMaxHeight())
        }
    }
}

@Composable
fun DateArea() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Thur 13, September",
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

@Composable
fun TasksList(modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(count = 6) { index: Int ->
            TasksListItem(modifier = Modifier.padding(vertical = 4.dp))
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun TasksListItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = .3.dp,
            color = Color.LightGray
        ),
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                Text("9:00")
                Text("9:30")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Column {
                Text("Meeting")
                Text("Meeting with Boss")
                Text("Meeting to discuss future projects with boss")
            }

        }
    }
}

@Preview
@Composable
fun NextPrevButtonPreview() {
    NextPrevButton()
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onAppNavigationAction = {})
}

@Preview
@Composable
fun TasksListItemPreview() {
    TasksListItem()
}