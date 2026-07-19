package com.example.doorshieldactuator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.doorshieldactuator.ui.theme.DarkGlassBackgroundBottom
import com.example.doorshieldactuator.ui.theme.DarkGlassBackgroundMiddle
import com.example.doorshieldactuator.ui.theme.DarkGlassBackgroundTop
import com.example.doorshieldactuator.ui.theme.LightGlassBackgroundBottom
import com.example.doorshieldactuator.ui.theme.LightGlassBackgroundMiddle
import com.example.doorshieldactuator.ui.theme.LightGlassBackgroundTop

@Composable
fun GlassBackground(
    content: @Composable BoxScope.() -> Unit
) {
    val isDark = isSystemInDarkTheme()

    val colors = if (isDark) {
        listOf(
            DarkGlassBackgroundTop,
            DarkGlassBackgroundMiddle,
            DarkGlassBackgroundBottom
        )
    } else {
        listOf(
            LightGlassBackgroundTop,
            LightGlassBackgroundMiddle,
            LightGlassBackgroundBottom
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors)
            ),
        content = content
    )
}