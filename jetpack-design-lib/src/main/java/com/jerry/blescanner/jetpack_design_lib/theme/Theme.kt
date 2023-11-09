package com.jerry.blescanner.jetpack_design_lib.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(

    primary = vusion_theme_light_primary,
    onPrimary = vusion_theme_light_onPrimary,
    primaryContainer = vusion_theme_light_primaryContainer,
    onPrimaryContainer = vusion_theme_light_onPrimaryContainer,
    secondary = vusion_theme_light_secondary,
    onSecondary = vusion_theme_light_onSecondary,
    secondaryContainer = vusion_theme_light_secondaryContainer,
    onSecondaryContainer = vusion_theme_light_onSecondaryContainer,
    tertiary = vusion_theme_light_tertiary,
    onTertiary = vusion_theme_light_onTertiary,
    tertiaryContainer = vusion_theme_light_tertiaryContainer,
    onTertiaryContainer = vusion_theme_light_onTertiaryContainer,
    error = vusion_theme_light_error,
    errorContainer = vusion_theme_light_errorContainer,
    onError = vusion_theme_light_onError,
    onErrorContainer = vusion_theme_light_onErrorContainer,
    background = vusion_theme_light_background,
    onBackground = vusion_theme_light_onBackground,
    surface = vusion_theme_light_surface,
    onSurface = vusion_theme_light_onSurface,
    surfaceVariant = vusion_theme_light_surfaceVariant,
    onSurfaceVariant = vusion_theme_light_onSurfaceVariant,
    outline = vusion_theme_light_outline,
    inverseOnSurface = vusion_theme_light_inverseOnSurface,
    inverseSurface = vusion_theme_light_inverseSurface,
)
private val DarkColorScheme = darkColorScheme(

    primary = vusion_theme_dark_primary,
    onPrimary = vusion_theme_dark_onPrimary,
    primaryContainer = vusion_theme_dark_primaryContainer,
    onPrimaryContainer = vusion_theme_dark_onPrimaryContainer,
    secondary = vusion_theme_dark_secondary,
    onSecondary = vusion_theme_dark_onSecondary,
    secondaryContainer = vusion_theme_dark_secondaryContainer,
    onSecondaryContainer = vusion_theme_dark_onSecondaryContainer,
    tertiary = vusion_theme_dark_tertiary,
    onTertiary = vusion_theme_dark_onTertiary,
    tertiaryContainer = vusion_theme_dark_tertiaryContainer,
    onTertiaryContainer = vusion_theme_dark_onTertiaryContainer,
    error = vusion_theme_dark_error,
    errorContainer = vusion_theme_dark_errorContainer,
    onError = vusion_theme_dark_onError,
    onErrorContainer = vusion_theme_dark_onErrorContainer,
    background = vusion_theme_dark_background,
    onBackground = vusion_theme_dark_onBackground,
    surface = vusion_theme_dark_surface,
    onSurface = vusion_theme_dark_onSurface,
    surfaceVariant = vusion_theme_dark_surfaceVariant,
    onSurfaceVariant = vusion_theme_dark_onSurfaceVariant,
    outline = vusion_theme_dark_outline,
    inverseOnSurface = vusion_theme_dark_inverseOnSurface,
    inverseSurface = vusion_theme_dark_inverseSurface,
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}