package com.flaye.kaihatsu.hiragana.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.flaye.kaihatsu.SpeechManager
import com.flaye.kaihatsu.hiragana.source.HiraganaRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestoreException
import java.util.*

class HiraganasListViewModel(
    hiraganaRemoteDataSource: HiraganaRemoteDataSource,
    private val speechManager: SpeechManager
) : ViewModel() {
    private val TAG = this::class.java.simpleName

    val hiraganaItemsLiveData = liveData {
        try {
            hiraganaRemoteDataSource.getHiraganas()
                .asLiveData()
                .map {
                    it.map { hiraganaDto ->
                        HiraganaItem(
                            hiraganaDto.kana,
                            hiraganaDto.hepburnRomanization,
                            hiraganaDto.ipaTranscription
                        )
                    }
                }
                .also { emitSource(it) }
        } catch (exception: FirebaseFirestoreException) {
            Log.w(TAG, "Error getting hiraganas", exception)
        }
    }

    fun speakHiragana(hiragana: String) {
        speechManager.speakText(hiragana, Locale.JAPANESE)
    }

}
