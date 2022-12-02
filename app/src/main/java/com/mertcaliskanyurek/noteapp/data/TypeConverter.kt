package com.mertcaliskanyurek.noteapp.data

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class TypeConverter {

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }

    @TypeConverter
    fun fromDateString(value: String?): Date? = value?.let {
        SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(it)
    }

    @TypeConverter
    fun dateToDateString(date: Date?): String? = date?.let {
        SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it)
    }
}