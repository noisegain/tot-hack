package com.itmem.tot_hack.ui.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itmem.tot_hack.domain.model.Message
import com.itmem.tot_hack.ui.utils.TimeUtils

@Composable
fun MessageFrom(
    isGroup: Boolean,
    message: Message = Message(),
    onClick: (Message) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .padding(end = 64.dp)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            if (isGroup) {
                Text(text = message.owner, style = MaterialTheme.typography.body1)
                Spacer(Modifier.height(2.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                MessageSurface(message, onClick)
                if (isGroup) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = message.rating.toString(), fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            TimeText(time = TimeUtils.convertDateToTime(message.date), TextAlign.Start)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MessageSurface(
    message: Message,
    onClick: (Message) -> Unit
) {
    val isShowing = remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colors.primary.copy(
            red = 0.5f,
            green = 0.5f,
            blue = 0.5f
        ),
        modifier = Modifier.padding(bottom = 4.dp),
        shape = RoundedCornerShape(
            topStartPercent = 25,
            topEndPercent = 25,
            bottomStartPercent = 0,
            bottomEndPercent = 25
        ),
        border = BorderStroke(message.rating.dp, MaterialTheme.colors.surface),
        onClick = {
            onClick(message)
        }
    ) {
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 10.dp
            )
        ) {
            if (isShowing.value || message.rating >= 0) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.body2
                )
            } else {
                Text("Message hidden")
                Text(
                    "Show",
                    modifier = Modifier.clickable { isShowing.value = true },
                    color = MaterialTheme.colors.primary
                )
            }
        }

    }
}