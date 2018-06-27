package ru.bit.espanola

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import java.util.*


@Entity
@TypeConverters(NativeMeansConverter::class)
data class Word(@PrimaryKey(autoGenerate = true) val id: Int = 0, var spanishName: String,
                val nativeMeans: String,
                var context: String? = null,
                var description: String? = null,
                var percent: Double = 0.0)

data class NativeMeans(var words: MutableList<Array<String>>)

class NativeMeansConverter {
    @TypeConverter
    fun storedStringToWords(value: String): NativeMeans {
        val langs = Arrays.asList(value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        return NativeMeans(langs)
    }

    @TypeConverter
    fun wordsToStoredString(means: NativeMeans): String {
        var value = ""

        for (lang in means.words)
            value += lang + ","

        return value
    }
}