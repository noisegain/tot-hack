package com.itmem.tot_hack.model

import com.itmem.tot_hack.domain.model.Message
import com.itmem.tot_hack.domain.model.User

data class ChatState(
    val user: User = User(),
    val messages: List<Message> = emptyList(),
    val selectedMessage: Message? = null
)
