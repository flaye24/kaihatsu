package com.flaye.kaihatsu.hiragana.source

import com.flaye.kaihatsu.hiragana.source.model.HiraganaDto

interface HiraganaRemoteDataSource {

    /**
     * Gets hiraganas from remote datasource
     */
    suspend fun getHiraganas(): List<HiraganaDto>

}