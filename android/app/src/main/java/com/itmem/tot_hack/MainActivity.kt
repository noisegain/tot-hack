package com.itmem.tot_hack

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.itmem.tot_hack.ui.detail.Chat
import com.itmem.tot_hack.ui.detail.ChatScreen
import com.itmem.tot_hack.ui.detail.GroupChat
import com.itmem.tot_hack.ui.detail.GroupChatScreen
import com.itmem.tot_hack.ui.detail.viewModel.ChatViewModel
import com.itmem.tot_hack.ui.theme.TothackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            TothackTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("chat") { Chat() }
                        composable("group") { GroupChat() }
                        composable("home") { HomeScreen(navController) }
                    }
                }
            }
        }
    }

    @Composable
    fun HomeScreen(navController: NavController) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                HomeButton(navController = navController, text = "Личный диалог", uri = "chat")
                HomeButton(navController = navController, text = "Групповой чат", uri = "group")
            }
        }
    }

    @Composable
    fun HomeButton(navController: NavController, text: String, uri: String) {
        Button(modifier = Modifier.width(300.dp), onClick = { navController.navigate(uri) }) {
            Text(text, style = MaterialTheme.typography.h5)
        }
    }
}