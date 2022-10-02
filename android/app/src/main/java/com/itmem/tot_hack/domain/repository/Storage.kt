package com.itmem.tot_hack.domain.repository

import com.itmem.tot_hack.domain.model.Message

interface Storage {
    suspend fun save(data: List<Message>)
    suspend fun read() : List<Message>
}