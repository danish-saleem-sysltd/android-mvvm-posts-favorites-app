package com.example.mvvm.postsfavorites.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val Primary = Color(0xFF3B5BDB)
private val OnPrimary = Color(0xFFFFFFFF)
private val PrimaryContainer = Color(0xFFDBE4FF)
private val OnPrimaryContainer = Color(0xFF0B1A66)

private val Secondary = Color(0xFF5C6BC0)
private val OnSecondary = Color(0xFFFFFFFF)

private val Background = Color(0xFFF7F7FB)
private val OnBackground = Color(0xFF1B1B1F)

private val Surface = Color(0xFFFFFFFF)
private val OnSurface = Color(0xFF1B1B1F)
private val SurfaceVariant = Color(0xFFE3E3E8)
private val OnSurfaceVariant = Color(0xFF45464F)

private val ErrorColor = Color(0xFFB3261E)
private val OnError = Color(0xFFFFFFFF)

internal val AppColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    error = ErrorColor,
    onError = OnError,
)

internal val AppColorsDark = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    background = Color(0xFF121316),
    onBackground = Color(0xFFE3E3E8),
    surface = Color(0xFF1B1B1F),
    onSurface = Color(0xFFE3E3E8),
)
