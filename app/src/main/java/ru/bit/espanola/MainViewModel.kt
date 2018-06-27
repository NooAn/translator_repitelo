package ru.bit.espanola

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.cloud.translate.Translate.TranslateOption
import com.google.cloud.translate.TranslateOptions


class MainViewModel : ViewModel() {
    val response = MutableLiveData<String>()

    fun getTranslationText(text: String) {
        val translate = TranslateOptions.getDefaultInstance().service

        val translation = translate.translate(
                text,
                TranslateOption.sourceLanguage("ru"),
                TranslateOption.targetLanguage("es"))
        
        response.value = translation?.translatedText ?: "..."
    }
}