package com.itmem.tot_hack.domain.repository

import com.itmem.tot_hack.domain.model.Message

interface MessagesRepository {
    suspend fun sendMessage(text: String)
    suspend fun loadMessages(): List<Message>
}