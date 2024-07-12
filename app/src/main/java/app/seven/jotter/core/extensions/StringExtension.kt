package app.seven.jotter.core.extensions

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


fun String.titleCase() = lowercase()
    .replaceFirstChar { it.uppercase() }
