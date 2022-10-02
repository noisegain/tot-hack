package com.itmem.tot_hack.ui.detail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itmem.tot_hack.core.log
import com.itmem.tot_hack.domain.model.Message
import com.itmem.tot_hack.domain.repository.CurrentUserProvider
import com.itmem.tot_hack.domain.repository.EmotionRepository
import com.itmem.tot_hack.domain.repository.MessagesRepository
import com.itmem.tot_hack.model.ChatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messagesRepo: MessagesRepository,
    private val emotionRepo: EmotionRepository,
    private val currentUserProvider: CurrentUserProvider
) : ViewModel() {

    var isGroup = false
    val title
        get() = if (isGroup) "Seven Bebras" else messages.value.user.name
    val emotion
        get() = if (isGroup) "" else messages.value.user.emotion

    private val initialState: ChatState = ChatState()
    private val _messages: MutableStateFlow<ChatState> = MutableStateFlow(initialState)
    val messages = _messages.asStateFlow()

    private var id = 0

    private val viewStateMutex = Mutex()

    private fun execute(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }

    private fun reduce(fn: suspend (ChatState) -> ChatState) = execute {
        viewStateMutex.lock()
        viewModelScope.launch {
            _messages.emit(
                fn(_messages.value)
            )
        }
        viewStateMutex.unlock()
    }

    init {
        recognizeEmotion("")
    }

    fun prepare() {
        currentUserProvider.setUserCnt(if (isGroup) 7 else 2)
    }

    fun sendMessage(text: String) = reduce {
        it.copy(
            messages = it.messages + Message(
                id = id++,
                text = text,
                owner = currentUserProvider.currentUser.name,
                date = Instant.now()
            )
        )
    }

    private fun recognizeEmotion(owner: String) = reduce {
        val emotion = emotionRepo.recognizeEmotion(_messages.value.messages, owner)
        it.copy(user = it.user.copy(emotion = emotion))
    }

    fun swapUser() {
        val oldUser = currentUserProvider.currentUser.name
        currentUserProvider.swapUser()
        reduce {
            it.copy(user = currentUserProvider.currentUser)
        }
        recognizeEmotion(oldUser)
    }

    fun selectMessage(message: Message) = reduce {
        it.copy(selectedMessage = message)
    }

    fun dismissSelected() = reduce {
        it.copy(selectedMessage = null)
    }

    fun vote(points: Int) = reduce {
        val msg = it.messages.withIndex().find { msg -> msg.value == it.selectedMessage } ?: return@reduce it
        it.copy(
            messages = it.messages.slice(0 until msg.index) + msg.value.copy(
                rating = msg.value.rating + points
            ) + it.messages.slice(msg.index + 1 until it.messages.size)
        ).also(::log)
    }
}