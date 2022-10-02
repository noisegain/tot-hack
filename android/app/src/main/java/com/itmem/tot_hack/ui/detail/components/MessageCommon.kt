package com.itmem.tot_hack.ui.detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TimeText(time: String = "Time", textAlign: TextAlign) {
    Text(
        text = time,
        textAlign = textAlign,
        modifier = Modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.caption,
        color = Color.Gray.copy(alpha = 0.75f)
    )
}