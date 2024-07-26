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
    fun stringToLocalTime(value: Long?): LocalTime? {
        return value?.let {
            Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalTime()
        }
    }

    @TypeConverter
    fun localTimeToString(time: LocalDate?): Long? {
        return time?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond()
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