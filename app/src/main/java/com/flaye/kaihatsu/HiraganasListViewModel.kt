package com.flaye.kaihatsu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await

class HiraganasListViewModel : ViewModel() {

    private val TAG = this::class.java.simpleName
    val hiraganaItemsLiveData =
        liveData<List<HiraganaItem>>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val firebaseFirestore = FirebaseFirestore.getInstance()
            try {
                val snapshot = firebaseFirestore.collection("hiraganas").get().await()
                val hiraganaItems = snapshot.toObjects(HiraganaItem::class.java)
                emit(hiraganaItems.sortedBy { it.kana })
            } catch (exception: FirebaseFirestoreException) {
                Log.w(TAG, "Error getting hiraganas.", exception)
            }
        }


}