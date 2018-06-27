package ru.bit.espanola

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.experimental.runBlocking
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by lazy { (activity?.applicationContext as KodeinAware).kodein }
    private val database: WordDatabase by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        newWord.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.getTranslationText(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        viewModel.response.observe(this, Observer { newValue.setText(it) })
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Added word: ${newWord.text.toString()}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            runBlocking {
                database.wordDao().insert(
                        Word(spanishName = newWord.text.toString(),
                                nativeMeans = (newValue.text.toString())))
            }
        }
        return v;
    }
}
