package com.itmem.tot_hack.ui.detail.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itmem.tot_hack.core.log
import com.itmem.tot_hack.domain.model.Message
import com.itmem.tot_hack.model.ChatState
import com.itmem.tot_hack.ui.detail.components.MessageFrom
import com.itmem.tot_hack.ui.detail.components.MessageTo
import com.itmem.tot_hack.ui.detail.viewModel.ChatViewModel
import timber.log.Timber

@Composable
fun MessagesList(
    modifier: Modifier = Modifier,
    state: State<ChatState>,
) {
    val messages = state.value.messages
    val vm = viewModel<ChatViewModel>()
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        log(messages.size)
        val onClick = { msg: Message ->
            vm.selectMessage(msg)
        }
        items(messages.asReversed()) { message ->
            if (message.owner == state.value.user.name) {
                MessageTo(message = message, onClick)
            } else {
                MessageFrom(isGroup = vm.isGroup, message = message, onClick)
            }
        }
    }
}