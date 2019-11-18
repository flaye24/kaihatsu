package com.flaye.kaihatsu.hiragana.source

import com.flaye.kaihatsu.hiragana.source.model.HiraganaDto
import kotlinx.coroutines.flow.Flow

interface HiraganaRemoteDataSource {

    /**
     * Gets hiraganas from remote datasource
     */
    suspend fun getHiraganas(): Flow<List<HiraganaDto>>

}