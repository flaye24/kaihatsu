package com.flaye.kaihatsu.hiragana.source

import android.util.Log
import com.flaye.kaihatsu.hiragana.source.model.HiraganaDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HiraganaRemoteRemoteDataSourceImpl(private val firestore: FirebaseFirestore) :
    HiraganaRemoteDataSource {
    private val TAG = HiraganaRemoteRemoteDataSourceImpl::class.java.simpleName

    @ExperimentalCoroutinesApi
    override suspend fun getHiraganas(): Flow<List<HiraganaDto>> = callbackFlow {
        val registration = firestore.collection("hiraganas")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.w(TAG, "Listen failed.", firebaseFirestoreException)
                    close(firebaseFirestoreException)
                    return@addSnapshotListener
                }
                querySnapshot?.toObjects<HiraganaDto>()?.let {
                    offer(it)
                }
            }

        awaitClose {
            registration.remove()
        }
    }


}