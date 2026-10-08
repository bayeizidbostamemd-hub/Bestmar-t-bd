package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
  primary = BestMartGreen,
  onPrimary = Color.White,
  primaryContainer = BestMartGreenContainer,
  onPrimaryContainer = BestMartGreenDark,
  secondary = BestMartOrange,
  onSecondary = Color.White,
  secondaryContainer = BestMartOrangeContainer,
  onSecondaryContainer = BestMartOrangeDark,
  tertiary = BestMartGreenLight,
  background = BackgroundOffWhite,
  onBackground = TextPrimaryDark,
  surface = SurfacePureWhite,
  onSurface = TextPrimaryDark,
  surfaceVariant = Color(0xFFF1F5F9),
  onSurfaceVariant = TextSecondaryMuted,
  outline = SurfaceBorder,
  error = ErrorRed,
  onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
  primary = BestMartGreenLight,
  onPrimary = Color.Black,
  primaryContainer = BestMartGreenDark,
  onPrimaryContainer = Color.White,
  secondary = BestMartOrangeLight,
  onSecondary = Color.Black,
  secondaryContainer = BestMartOrangeDark,
  onSecondaryContainer = Color.White,
  tertiary = BestMartOrange,
  background = Color(0xFF0F172A),
  onBackground = Color(0xFFF8FAFC),
  surface = Color(0xFF1E293B),
  onSurface = Color(0xFFF8FAFC),
  surfaceVariant = Color(0xFF334155),
  onSurfaceVariant = Color(0xFFCBD5E1),
  outline = Color(0xFF475569),
  error = ErrorRed,
  onError = Color.White
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // For BestMart BD, prioritize our custom curated White, Green & Orange branding
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        window.statusBarColor = colorScheme.surface.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
      }
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
