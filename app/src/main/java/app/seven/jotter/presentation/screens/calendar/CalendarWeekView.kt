package app.seven.jotter.presentation.screens.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import app.seven.jotter.presentation.components.CircularAvatar
import app.seven.jotter.presentation.theme.pallet
import app.seven.jotter.presentation.theme.spacing
import app.seven.jotter.presentation.theme.textSize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

val listOfWeekDays =
    listOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat")

val mapOfDaysAndValue = mapOf(
    DayOfWeek.SUNDAY to 0,
    DayOfWeek.MONDAY to 1,
    DayOfWeek.TUESDAY to 2,
    DayOfWeek.WEDNESDAY to 3,
    DayOfWeek.THURSDAY to 4,
    DayOfWeek.FRIDAY to 5,
    DayOfWeek.SATURDAY to 6,
)

@Composable
fun CalendarView() {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOfWeekDays.map {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(spacing().small),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it,
                        fontSize = textSize().lSmall,
                        color = Color.Gray
                    )
                }
            }
        }
        AnimatedVisibility(isExpanded) {
            CalendarMonthView()
        }

        AnimatedVisibility(!isExpanded) {
            CalendarWeekView()
        }

        Row(
            modifier = Modifier.clickable {
                isExpanded = !isExpanded
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isExpanded) "Collapse" else "Expand",
                fontSize = textSize().lSmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.size(spacing().small))
            Icon(
                modifier = Modifier.size(spacing().lSmall),
                imageVector = if (isExpanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                tint = Color.Gray,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun CalendarWeekView() {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek

    val dayValue = when (dayOfWeek) {
        DayOfWeek.SUNDAY -> 0
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 4
        DayOfWeek.SATURDAY -> 6
        else -> 0
    }

    val monday = today.minusDays(dayValue.toLong())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            listOf(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY,
            ).map {
                val day = monday.plusDays(mapOfDaysAndValue[it]!!.toLong())
                DayItem(
                    modifier = Modifier.weight(1f),
                    day = day.dayOfMonth,
                    isToday = dayOfWeek == it,
                )
            }
        }
    }
}

@Composable
fun CalendarMonthView() {

    val today = LocalDate.now()
    val daysOfMonthChucked = getDaysOfMonth(today.year, today.month)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        daysOfMonthChucked.map { list ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                list.map { day ->
                    DayItem(
                        modifier = Modifier.weight(1f),
                        day = day,
                        isToday = day == today.dayOfMonth,
                    )
                }
            }
        }
    }
}

private fun getDaysOfMonth(year: Int, month: Month): List<List<Int>> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)

    val arrayOfDays = Array(35) { -1 }

    fun isLeapYear(year: Int): Boolean {
        // If the year is evenly divisible by 4, go to step 2. Otherwise, go to step 5.
        // If the year is evenly divisible by 100, go to step 3. Otherwise, go to step 4.
        // If the year is evenly divisible by 400, go to step 4. Otherwise, go to step 5.
        // The year is a leap year (it has 366 days).
        // The year is not a leap year (it has 365 days).

        if (year % 4 == 0 && year % 100 != 0) return true
        if (year % 4 == 0 && year % 100 == 0 && year % 400 == 0) return true
        return false
    }

    fun lastDayOfMonth(year: Int, month: Month) = when (month) {
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> {
            if (isLeapYear(year)) 29 else 28
        }

        else -> {
            31
        }
    }

    var day = 1
    for (i in mapOfDaysAndValue[firstDayOfMonth.dayOfWeek]!! until arrayOfDays.size) {
        if (day > lastDayOfMonth(year, month)) {
            break
        }

        arrayOfDays[i] = day++
    }

    return arrayOfDays.toList().chunked(7)
}

@Composable
fun DayItem(modifier: Modifier = Modifier, isToday: Boolean = false, day: Int) {
    Column(
        modifier = modifier.padding(spacing().small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularAvatar(
            backgroundColor = if (isToday) pallet().primary else Color.Transparent
        ) {
            Text(text = if (day == -1) "" else "$day")
        }
    }
}

@Preview
@Composable
fun CalendarWeekViewPreview() {
    Surface {
        CalendarWeekView()
    }
}

@Preview
@Composable
fun CalendarMonthViewPreview() {
    Surface {
        CalendarMonthView()
    }
}