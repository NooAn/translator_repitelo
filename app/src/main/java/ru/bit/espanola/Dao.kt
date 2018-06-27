package ru.bit.espanola

import android.arch.persistence.room.*
import kotlinx.coroutines.experimental.async

@Dao
@TypeConverters(NativeMeansConverter::class)
interface WordsDao {
    @Insert
    fun insertWord(word: Word)

    @Query("SELECT * FROM word WHERE id=:id")
    fun oneWord(id: Int): Word?

    @Query("SELECT * FROM word")
    fun allWords(): List<Word>

    @Query("SELECT COUNT(id) FROM word")
    fun count(): Int
}

suspend fun WordsDao.getCount(): Int = async { count() }.await()

suspend fun WordsDao.insert(word: Word) = async { insertWord(word) }.await()

suspend fun WordsDao.getOneWord(id: Int): Word? = async { oneWord(id) }.await()

suspend fun WordsDao.getAllWords(): List<Word> = async { allWords() }.await()

@Database(entities = [(Word::class)], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao(): WordsDao
}
