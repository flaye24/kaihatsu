package com.flaye.kaihatsu.hiragana.source

import com.flaye.kaihatsu.hiragana.source.model.HiraganaDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HiraganaRemoteRemoteDataSourceImpl(private val firestore: FirebaseFirestore) :
    HiraganaRemoteDataSource {

    override suspend fun getHiraganas(): List<HiraganaDto> {
        return firestore.collection("hiraganas").get()
            .await()
            .toObjects(HiraganaDto::class.java)
    }

}