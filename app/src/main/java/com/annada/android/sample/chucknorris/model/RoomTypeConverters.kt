package com.annada.android.sample.chucknorris.model

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

class RoomTypeConverters {
        @TypeConverter
        fun restoreList(listOfString: String?): List<String?>? {
            return Gson().fromJson<List<String?>>(
                listOfString,
                object : TypeToken<List<String?>?>() {}.type
            )
        }

        @TypeConverter
        fun saveList(listOfString: List<String?>?): String? {
            return Gson().toJson(listOfString)
        }
}

