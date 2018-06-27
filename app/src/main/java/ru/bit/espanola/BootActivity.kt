package ru.bit.espanola

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_boot.*
import kotlinx.coroutines.experimental.runBlocking
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import java.util.*

class BootActivity : Activity(), KodeinAware {
    override val kodein: Kodein by lazy { (applicationContext as KodeinAware).kodein }
    private val random = Random()
    private var answer: String = ""
    private val database: WordDatabase by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boot)
        val listAnswer = arrayListOf<String>("", "", "", "")

        runBlocking {
            val end = database.wordDao().getCount()
            val r = random(0, end)
            for (i in 0..3) {
                listAnswer[i] = database.wordDao().getOneWord(random(0, end))?.nativeMeans.toString()
            }
            val word = database.wordDao().getOneWord(r)
            listAnswer[r] = word?.nativeMeans.toString()
            answer = word?.nativeMeans.toString()
            Log.e("LOG", "r:$r  ${word?.spanishName}")
            nameOfWord.text = word?.spanishName

            variantOne.text = listAnswer[0]
            variantTwo.text = listAnswer[1]
            variantThree.text = listAnswer[2]
            variantFour.text = listAnswer[3]

            variantOne.click()
            variantTwo.click()
            variantThree.click()
            variantFour.click()
        }
    }

    private fun TextView.click() {
        this.setOnClickListener {
            clickOnVariant(this)
        }
    }

    private fun clickOnVariant(suggestion: TextView) {
        if (suggestion.text.toString().equals(answer)) {
            suggestion.setBackgroundColor(ContextCompat.getColor(this, R.color.right))
        } else {
            suggestion.setBackgroundColor(ContextCompat.getColor(this, R.color.wrong))
        }
        clMain.setOnClickListener { finish() }
    }


    fun random(start: Int, end: Int): Int {
        return random.nextInt(end - start) + start
    }
}

