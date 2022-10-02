package com.itmem.tot_hack.domain.model

import java.time.Instant
import java.time.LocalDateTime
import java.util.*

data class Message(
    val id: Int = 0,
    val text: String = "",
    val owner: String = "anon",
    val date: Instant = Instant.now(),
    val rating: Int = 0
)
