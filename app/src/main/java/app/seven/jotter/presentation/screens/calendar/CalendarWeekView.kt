package app.seven.jotter.presentation.screens.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import app.seven.jotter.common.extensions.titleCase
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

enum class CalenderMode {
    MONTH, WEEK
}

@Composable
fun CalendarView(calenderMode: CalenderMode, date: LocalDate, onDateChanged: (LocalDate) -> Unit) {
    val isMonthView = calenderMode == CalenderMode.MONTH

    fun nextDate(isMonth: Boolean) {
        onDateChanged(
            if (isMonth) {
                date.plusMonths(1)
            } else {
                date.plusWeeks(1)
            }
        )
    }

    fun prevDate(isMonth: Boolean) {
        onDateChanged(
            if (isMonth) {
                date.minusMonths(1)
            } else {
                date.minusWeeks(1)
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        HeaderView(
            month = if (isMonthView || date.dayOfMonth / 7 > 0) {
                date.month
            } else {
                date.minusMonths(1L).month
            }.name.titleCase(),
            onNext = { nextDate(isMonthView) },
            onPrevious = { prevDate(isMonthView) }
        )

        WeekHeader()

        AnimatedVisibility(isMonthView) {
            CalendarMonthView(date = date)
        }

        AnimatedVisibility(!isMonthView) {
            CalendarWeekView(date = date)
        }

        /* Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(spacing.xSmall)
                 .clickable { isExpanded = !isExpanded },
             verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.Center
         ) {
             Text(
                 text = if (isExpanded) "Collapse" else "Expand",
                 fontSize = textSize.lSmall,
                 color = Color.Gray
             )
             Spacer(modifier = Modifier.size(spacing.small))
             Icon(
                 modifier = Modifier.size(spacing.lSmall),
                 imageVector = if (isExpanded) {
                     Icons.Default.KeyboardArrowUp
                 } else {
                     Icons.Default.KeyboardArrowDown
                 },
                 tint = Color.Gray,
                 contentDescription = ""
             )
         }*/
    }
}

@Composable
fun CalendarWeekView(date: LocalDate) {
    val today = LocalDate.now()
    val dayOfWeek = date.dayOfWeek
    val monday = date.minusDays(mapOfDaysAndValue[dayOfWeek]!!.toLong())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            mapOfDaysAndValue.keys.map {
                val day = monday.plusDays(mapOfDaysAndValue[it]!!.toLong())
                DayItem(
                    modifier = Modifier.weight(1f),
                    day = day.dayOfMonth,
                    isToday = day == today,
                )
            }
        }
    }
}

@Composable
fun CalendarMonthView(date: LocalDate) {
    val today = LocalDate.now()
    val daysOfMonthChucked = getDaysOfMonth(date.year, date.month)

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
                        day = day.dayOfMonth,
                        isToday = day == today,
                        differentMonth = day.month != date.month,
                    )
                }
            }
        }
    }
}

private fun getDaysOfMonth(year: Int, month: Month): List<List<LocalDate>> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)

    val arrayOfDates = Array<LocalDate>(35) { LocalDate.now() }
    val daySlot = firstDayOfMonth.minusDays(mapOfDaysAndValue[firstDayOfMonth.dayOfWeek]!!.toLong())

    for (i in arrayOfDates.indices) {
        arrayOfDates[i] = daySlot.plusDays(i.toLong())
    }

    return arrayOfDates.toList().chunked(7)
}

@Composable
fun DayItem(
    modifier: Modifier = Modifier,
    isToday: Boolean = false,
    differentMonth: Boolean = false,
    day: Int
) {
    Column(
        modifier = modifier.padding(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularAvatar(
            backgroundColor = if (isToday) pallet.primary else Color.Transparent
        ) {
            Text(
                text = if (day == -1) "" else "$day",
                color = if (differentMonth) Color.LightGray else Color.Unspecified
            )
        }
    }
}

@Composable
fun WeekHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        listOfWeekDays.map {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(spacing.small),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = it,
                    fontSize = textSize.lSmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun HeaderView(
    month: String,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPrevious) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.size(spacing.small))
        Text(
            modifier = Modifier.weight(1f),
            text = month.titleCase(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(spacing.small))
        IconButton(onClick = onNext) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun CalendarViewPreview() {
    Surface {
        CalendarView(calenderMode = CalenderMode.MONTH, date = LocalDate.now()){}
    }
}

@Preview
@Composable
fun CalendarWeekViewPreview() {
    Surface {
        CalendarWeekView(LocalDate.now())
    }
}

@Preview
@Composable
fun CalendarMonthViewPreview() {
    Surface {
        CalendarMonthView(LocalDate.now())
    }
}
