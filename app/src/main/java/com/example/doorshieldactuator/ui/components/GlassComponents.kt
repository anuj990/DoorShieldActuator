package com.example.doorshieldactuator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    elevation: Dp = 6.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable BoxScope.() -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val shape = RoundedCornerShape(cornerRadius)

    val backgroundBrush = Brush.verticalGradient(
        colors = if (isDark) {
            listOf(
                MaterialTheme.colorScheme.surface.copy(alpha = 0.68f),
                MaterialTheme.colorScheme.surface.copy(alpha = 0.38f)
            )
        } else {
            listOf(
                Color.White.copy(alpha = 0.62f),
                Color.White.copy(alpha = 0.30f)
            )
        }
    )

    val borderBrush = Brush.verticalGradient(
        colors = if (isDark) {
            listOf(
                Color.White.copy(alpha = 0.22f),
                Color.White.copy(alpha = 0.06f)
            )
        } else {
            listOf(
                Color.White.copy(alpha = 0.95f),
                Color.White.copy(alpha = 0.30f)
            )
        }
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                clip = false,
                ambientColor = if (isDark) {
                    Color.Black.copy(alpha = 0.35f)
                } else {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
                },
                spotColor = if (isDark) {
                    Color.Black.copy(alpha = 0.45f)
                } else {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                }
            )
            .clip(shape)
            .background(backgroundBrush)
            .border(
                width = 1.dp,
                brush = borderBrush,
                shape = shape
            )
            .padding(contentPadding),
        content = content
    )
}