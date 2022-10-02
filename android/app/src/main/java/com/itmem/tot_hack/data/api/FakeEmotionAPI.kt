package com.itmem.tot_hack.data.api

import javax.inject.Inject

class FakeEmotionAPI @Inject constructor() : EmotionAPI {
    override suspend fun recognize(messages: String): String {
        return listOf("\uD83D\uDE21", "\uD83D\uDE04", "\uD83D\uDE14").random()
    }
}// 😭😄😔😊😍🙈🥰🥹😡