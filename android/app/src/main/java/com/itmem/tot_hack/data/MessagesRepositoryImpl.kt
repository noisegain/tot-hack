package com.itmem.tot_hack.data

import com.itmem.tot_hack.domain.model.Message
import com.itmem.tot_hack.domain.repository.MessagesRepository
import javax.inject.Inject


class MessagesRepositoryImpl @Inject constructor() : MessagesRepository {
    override suspend fun sendMessage(text: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loadMessages(): List<Message> {
        TODO("Not yet implemented")
    }
}