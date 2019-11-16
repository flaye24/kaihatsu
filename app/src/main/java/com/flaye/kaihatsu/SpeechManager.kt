package com.flaye.kaihatsu

import java.util.*

interface SpeechManager {

    /**
     * Speaks the text provided
     * @param text : the text to speak
     * @param languageLocale : the [Locale] of the language to speak the text
     */
    fun speakText(text: String, languageLocale: Locale)
}