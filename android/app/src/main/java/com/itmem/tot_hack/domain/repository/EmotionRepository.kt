package com.itmem.tot_hack.domain.repository

import com.itmem.tot_hack.domain.model.Message

interface EmotionRepository {
    suspend fun recognizeEmotion(messages: List<Message>, owner: String): String
}