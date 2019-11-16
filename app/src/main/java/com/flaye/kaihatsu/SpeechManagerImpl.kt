package com.flaye.kaihatsu

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.Model
import java.util.*


class SpeechManagerImpl(context: Context) : SpeechManager, TextToSpeech.OnInitListener {
    private var ttsInitialized = false
    var tts: TextToSpeech = TextToSpeech(context, this)


    override fun onInit(status: Int) {
        ttsInitialized = status == TextToSpeech.SUCCESS
    }

    override fun speakText(text: String, languageLocale: Locale) {
        if (ttsInitialized && tts.isLanguageAvailable(languageLocale) == TextToSpeech.LANG_AVAILABLE) {
            tts.language = languageLocale
            tts.speak(
                text,
                TextToSpeech.QUEUE_FLUSH,
                null,
                System.currentTimeMillis().toString()
            )
        }
    }
}