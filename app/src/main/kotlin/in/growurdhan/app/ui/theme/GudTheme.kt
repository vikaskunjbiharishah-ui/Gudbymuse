package `in`.growurdhan.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Brand identity carried over from the earlier project: navy, gold, teal.
// These are the only brand colours in the shell. No other palette is used.
private val GudNavy = Color(0xFF07131F)
private val GudNavyLight = Color(0xFF8AA8C8)
private val GudGold = Color(0xFFF5C14A)
private val GudTeal = Color(0xFF2DD4BF)
private val GudTealDark = Color(0xFF0F766E)
private val GudDarkBackground = Color(0xFF0B1622)
private val GudDarkSurface = Color(0xFF122233)

private val GudLightColors = lightColorScheme(
    primary = GudNavy,
    onPrimary = Color.White,
    secondary = GudTealDark,
    onSecondary = Color.White,
    tertiary = GudGold,
    onTertiary = GudNavy,
    background = Color.White,
    onBackground = GudNavy,
    surface = Color.White,
    onSurface = GudNavy,
)

private val GudDarkColors = darkColorScheme(
    primary = GudNavyLight,
    onPrimary = GudNavy,
    secondary = GudTeal,
    onSecondary = GudNavy,
    tertiary = GudGold,
    onTertiary = GudNavy,
    background = GudDarkBackground,
    onBackground = Color.White,
    surface = GudDarkSurface,
    onSurface = Color.White,
)

/**
 * The single theme for the whole app. Screens do not define their own
 * colours, text styles, or shapes outside this theme and GudComponents.
 */
@Composable
fun GudTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) GudDarkColors else GudLightColors,
        typography = Typography(),
        content = content,
    )
}
