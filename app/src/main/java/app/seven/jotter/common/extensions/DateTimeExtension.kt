package app.seven.jotter.common.extensions

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

fun LocalDate.formatDate(): String {
    if (this.isEqual(LocalDate.now())) {
        return "Today"
    }

    if (this.isEqual(LocalDate.now().plusDays(1))) {
        return "Tomorrow"
    }

    return format(DateTimeFormatter.ofPattern("EEE, dd MMM"))
}

fun LocalTime.formatTime(): String {
    return this.format(
        DateTimeFormatter.ofPattern("hh:mm a")
    ).uppercase()
}

fun Long.convertMillisToDate(): LocalDate =
    Date(this).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
