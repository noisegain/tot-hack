package com.itmem.tot_hack.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itmem.tot_hack.domain.model.Message
import com.itmem.tot_hack.ui.utils.TimeUtils

@Composable
fun MessageTo(
    message: Message = Message(),
    onClick: (Message) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 64.dp)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.End
        ) {
            MessageSurface(message, onClick)
            if (message.id == -1) {
                TimeText(time = "Sending", textAlign = TextAlign.Right)
            } else {
                TimeText(time = TimeUtils.convertDateToTime(message.date), textAlign = TextAlign.Right)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MessageSurface(
    message: Message,
    onClick: (Message) -> Unit
) {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier.padding(bottom = 4.dp),
        shape = RoundedCornerShape(
            topStartPercent = 25,
            topEndPercent = 25,
            bottomStartPercent = 25,
            bottomEndPercent = 0
        ),
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
            Text(
                text = message.text,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}