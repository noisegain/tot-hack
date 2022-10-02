package com.itmem.tot_hack.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itmem.tot_hack.core.log
import com.itmem.tot_hack.model.ChatState
import com.itmem.tot_hack.ui.detail.components.MessageTextField
import com.itmem.tot_hack.ui.detail.components.MessagesList
import com.itmem.tot_hack.ui.detail.viewModel.ChatViewModel
import kotlinx.coroutines.launch


@Composable
fun GroupChat() {
    val vm = hiltViewModel<ChatViewModel>()
    vm.isGroup = true
    vm.prepare()
    GroupChatScreen(vm.messages.collectAsState())
}

@Composable
fun Chat() {
    val vm = hiltViewModel<ChatViewModel>()
    ChatScreen(vm.messages.collectAsState())
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GroupChatScreen(
    state: State<ChatState>,
) {
    val viewModel = hiltViewModel<ChatViewModel>()
    log(state.value.user)

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = state.value.selectedMessage, block = {
        scope.launch {
            if (state.value.selectedMessage != null) {
                sheetState.show()
                log("hiding")
            } else {
                sheetState.hide()
                log("showing")
            }
        }
    })

    ModalBottomSheetLayout(
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize(),
        sheetElevation = 0.dp,
        sheetContent = {
            Voting(viewModel)
        }) {
        Scaffold(
            topBar = {
                ChatTitleBar(
                    viewModel.title,
                    viewModel.emotion
                )
            },
        ) { paddingValues ->
            val messages = state.value.messages
            log("We are recomposing: ${messages.size}")
            val messageText = remember { mutableStateOf("") }
            ChatColumn(
                paddingValues = paddingValues,
                onClickSendButton = {
                    if (messageText.value.isNotBlank()) viewModel.sendMessage(messageText.value.also {
                        messageText.value = ""
                    })
                },
                state = state,
                messageText = messageText.value,
                onMessageTextChanged = { messageText.value = it },
            )
        }
    }
}

@Composable
fun ChatScreen(
    state: State<ChatState>,
) {
    val viewModel = hiltViewModel<ChatViewModel>()
    log(state.value.user)

    Scaffold(
        topBar = {
            ChatTitleBar(
                viewModel.title,
                viewModel.emotion
            )
        },
    ) { paddingValues ->
        val messages = state.value.messages
        log("We are recomposing: ${messages.size}")
        val messageText = remember { mutableStateOf("") }
        ChatColumn(
            paddingValues = paddingValues,
            onClickSendButton = {
                if (messageText.value.isNotBlank()) viewModel.sendMessage(messageText.value.also {
                    messageText.value = ""
                })
            },
            state = state,
            messageText = messageText.value,
            onMessageTextChanged = { messageText.value = it },
        )
    }
}

@Composable
private fun Voting(
    viewModel: ChatViewModel
) {
    val voting = { it: Int ->
        viewModel.vote(it)
        viewModel.dismissSelected()
    }

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { voting(1) }) {
            Icon(
                imageVector = Icons.Filled.ArrowUpward, contentDescription = "upvote"
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        IconButton(onClick = { voting(-1) }) {
            Icon(
                imageVector = Icons.Filled.ArrowDownward, contentDescription = "downvote"
            )
        }
    }
}

@Composable
fun ChatColumn(
    paddingValues: PaddingValues = PaddingValues(all = 0.dp),
    onClickSendButton: () -> Unit = {},
    state: State<ChatState>,
    messageText: String,
    onMessageTextChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(modifier = Modifier.weight(1f, true)) {
            MessagesList(
                modifier = Modifier
                    .fillMaxSize(),
                state = state,
            )
        }
        MessageTextField(
            messageText = messageText,
            onMessageTextChanged = onMessageTextChanged,
            onClickSendButton = { onClickSendButton() }
        )
    }
}

@Composable
fun ChatTitleBar(
    title: String,
    emotion: String
) {
    val viewModel = hiltViewModel<ChatViewModel>()
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = emotion)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { viewModel.swapUser() }) {
                    Icon(
                        imageVector = Icons.Filled.SwapHoriz,
                        "swap",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        },
//        navigationIcon = {
//            IconButton(onClick = { /*navController.popBackStack()*/ }) {
//                Icon(
//                    imageVector = Icons.Filled.ArrowBack,
//                    contentDescription = "Navigate Back"
//                )
//            }
//        },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 1.dp,
    )
}