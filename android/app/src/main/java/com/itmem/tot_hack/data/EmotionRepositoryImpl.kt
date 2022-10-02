package com.itmem.tot_hack.data;

import com.itmem.tot_hack.data.api.EmotionAPI
import com.itmem.tot_hack.domain.model.Message
import com.itmem.tot_hack.domain.repository.EmotionRepository
import javax.inject.Inject

class EmotionRepositoryImpl @Inject constructor(
    private val emotionAPI: EmotionAPI
) : EmotionRepository {
    override suspend fun recognizeEmotion(messages: List<Message>, owner: String) = emotionAPI.recognize(
        messages.asReversed().asSequence()
            .filter { it.owner == owner }.take(5).map { it.text }.joinToString("$")
    )
}
