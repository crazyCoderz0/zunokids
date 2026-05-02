package com.crazycoderz.zunokids.data

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TtsManager(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var isReady = false

    init {
        tts = TextToSpeech(context) { status ->
            isReady = status == TextToSpeech.SUCCESS
            if (!isReady) Log.e("TTS", "Initialization failed")
        }
    }

    fun speak(text: String, languageCode: String) {
        if (!isReady) return
        val locale = when (languageCode) {
            "es" -> Locale("es", "ES")
            "zh" -> Locale.CHINESE
            "hi" -> Locale("hi", "IN")
            "fr" -> Locale.FRENCH
            else -> Locale.US
        }
        val result = tts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA ||
            result == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.setLanguage(Locale.US)
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "kidslearn_$text")
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }
}
