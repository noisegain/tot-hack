package com.itmem.tot_hack.domain.service

import com.itmem.tot_hack.domain.model.Emotion

interface EmotionService {
    suspend fun recognizeEmotion(text: String): Emotion
}