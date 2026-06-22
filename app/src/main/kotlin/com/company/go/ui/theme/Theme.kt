package com.company.go.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = BackgroundLight,
    primaryContainer = PrimaryBlueLight,
    onPrimaryContainer = PrimaryBlueDark,
    secondary = SecondaryGrayDark,
    onSecondary = BackgroundLight,
    secondaryContainer = SecondaryGray,
    onSecondaryContainer = SecondaryGrayBlack,
    tertiary = AccentOrange,
    onTertiary = BackgroundLight,
    tertiaryContainer = AccentOrange.copy(alpha = 0.2f),
    onTertiaryContainer = AccentOrange,
    error = ColorError,
    onError = BackgroundLight,
    errorContainer = ColorError.copy(alpha = 0.1f),
    onErrorContainer = ColorError,
    background = BackgroundLight,
    onBackground = SecondaryGrayBlack,
    surface = BackgroundLight,
    onSurface = SecondaryGrayBlack,
    surfaceVariant = SecondaryGray,
    onSurfaceVariant = SecondaryGrayDark,
    outline = SecondaryGrayDark,
    outlineVariant = SecondaryGray
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlueLight,
    onPrimary = PrimaryBlueDark,
    primaryContainer = PrimaryBlueDark,
    onPrimaryContainer = PrimaryBlueLight,
    secondary = SecondaryGray,
    onSecondary = SecondaryGrayBlack,
    secondaryContainer = SecondaryGrayDark,
    onSecondaryContainer = SecondaryGray,
    tertiary = AccentOrange.copy(alpha = 0.8f),
    onTertiary = SecondaryGrayBlack,
    tertiaryContainer = AccentOrange.copy(alpha = 0.3f),
    onTertiaryContainer = AccentOrange,
    error = ColorError,
    onError = SecondaryGrayBlack,
    errorContainer = ColorError.copy(alpha = 0.2f),
    onErrorContainer = ColorError,
    background = BackgroundDark,
    onBackground = BackgroundLight,
    surface = BackgroundDark,
    onSurface = BackgroundLight,
    surfaceVariant = SecondaryGrayDark.copy(alpha = 0.5f),
    onSurfaceVariant = SecondaryGray,
    outline = SecondaryGray,
    outlineVariant = SecondaryGrayDark
)

@Composable
fun GOcompanyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = GOcompanyTypography,
        shapes = GOcompanyShapes,
        content = content
    )
}
