package com.flaye.kaihatsu.hiragana.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.flaye.kaihatsu.SpeechManager
import com.flaye.kaihatsu.hiragana.source.HiraganaRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import java.util.*

class HiraganasListViewModel(
    hiraganaRemoteDataSource: HiraganaRemoteDataSource,
    private val speechManager: SpeechManager
) : ViewModel() {
    private val TAG = this::class.java.simpleName

    val hiraganaItemsLiveData =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            try {
                val hiraganaItems = hiraganaRemoteDataSource.getHiraganas()
                    .sortedBy { it.kana }
                    .map {
                        HiraganaItem(
                            it.kana,
                            it.hepburnRomanization,
                            it.ipaTranscription
                        )
                    }
                emit(hiraganaItems)
            } catch (exception: FirebaseFirestoreException) {
                Log.w(TAG, "Error getting hiraganas", exception)
            }
        }

    fun speakHiragana(hiragana: String) {
        speechManager.speakText(hiragana, Locale.JAPANESE)
    }

}
