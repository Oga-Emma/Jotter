package app.seven.jotter.core.utils

import androidx.room.TypeConverter
import app.seven.jotter.core.models.TaskCheckListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId


class RoomTypeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let {
            Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDate()
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond()
    }

    @TypeConverter
    fun stringToLocalTime(value: String?): LocalTime? {
        try {
            if (value != null) {
                val split = value.split(":")
                if (split.size == 3) {

                    return LocalTime.of(split[0].toInt(), split[1].toInt(), split[2].toInt())
                }
            }
        } catch (_: Exception) { }

        return LocalTime.now()
    }

    @TypeConverter
    fun localTimeToString(time: LocalTime?): String? {
        return time?.let {
            "${it.hour}:${it.minute}:${it.second}"
        }
    }

    @TypeConverter
    fun stringToListOfTaskCheckListItemEntity(rawString: String?): List<TaskCheckListItem>? {
        return rawString?.let {
            val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun listOfTaskCheckListItemEntityToString(list: List<TaskCheckListItem>?): String? {
        return list?.let { Gson().toJson(it) }
    }
}