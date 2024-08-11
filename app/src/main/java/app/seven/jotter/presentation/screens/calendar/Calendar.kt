package app.seven.jotter.presentation.screens.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CalendarViewWeek
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var calenderMode by rememberSaveable { mutableStateOf(CalenderMode.WEEK) }
    var date by rememberSaveable { mutableStateOf(LocalDate.now()) }

    Column {
        TopAppBar(title = { Text(text = "Calendar") }, actions = {
            TextButton(onClick = {
                date = LocalDate.now()
            }) {
                Text(text = "Today")
            }
            IconButton(onClick = {
                calenderMode = when (calenderMode) {
                    CalenderMode.MONTH -> CalenderMode.WEEK
                    CalenderMode.WEEK -> CalenderMode.MONTH
                }
            }) {
                Icon(
                    when (calenderMode) {
                        CalenderMode.MONTH -> Icons.Outlined.CalendarViewWeek
                        CalenderMode.WEEK -> Icons.Outlined.CalendarMonth
                    }, contentDescription = "Toggle calender view"
                )
            }
        })
        CalendarView(calenderMode,
            date = date,
            onDateChanged = { date = it }
        )
        HorizontalDivider(
            thickness = .5.dp,
            color = Color.LightGray
        )
        CalendarEvents()
    }
}