package com.company.go.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.glassomorphic(
    alpha: Float = 0.85f,
    blurRadius: Float = 15f,
    borderColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    borderWidth: Float = 1f
): Modifier = this
    .blur(radius = blurRadius.dp)
    .background(
        color = MaterialTheme.colorScheme.surface.copy(alpha = alpha),
        shape = RoundedCornerShape(24.dp)
    )

val LightGlassAlpha = 0.75f
val LightGlassBlur = 12f
val LightGlassBorder = Color.White.copy(alpha = 0.4f)

val DarkGlassAlpha = 0.8f
val DarkGlassBlur = 15f
val DarkGlassBorder = Color.White.copy(alpha = 0.2f)

val MiuixGradientPrimary = Brush.verticalGradient(colors = listOf(PrimaryBlue, PrimaryBlueLight))
val MiuixGradientAccent = Brush.verticalGradient(colors = listOf(AccentOrange, AccentOrange.copy(alpha = 0.8f)))

object MiuixElevation {
    val Level0 = 0.dp
    val Level1 = 2.dp
    val Level2 = 4.dp
    val Level3 = 8.dp
    val Level4 = 12.dp
    val Level5 = 16.dp
    val Level6 = 24.dp
}

object MiuixSpacing {
    val ExtraSmall = 4.dp
    val Small = 8.dp
    val Medium = 12.dp
    val Large = 16.dp
    val ExtraLarge = 24.dp
    val Huge = 32.dp
}

object MiuixCorner {
    val Small = 8.dp
    val Medium = 12.dp
    val Large = 16.dp
    val ExtraLarge = 28.dp
    val Full = 50.dp
}
