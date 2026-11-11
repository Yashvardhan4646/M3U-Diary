package com.example.drdiary.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.drdiary.data.AppThemePalette

// 1. Purple Theme - Material 3 Light Color Scheme roles (exact as specified)
val purplePrimary = Color(0xFF6750A4)
val purpleOnPrimary = Color(0xFFFFFFFF)
val purplePrimaryContainer = Color(0xFFEADDFF)
val purpleOnPrimaryContainer = Color(0xFF21005D)

val purpleSecondary = Color(0xFF635A75)
val purpleOnSecondary = Color(0xFFFFFFFF)
val purpleSecondaryContainer = Color(0xFFE8DEF8)
val purpleOnSecondaryContainer = Color(0xFF1D192B)

val purpleTertiary = Color(0xFF7D5260)
val purpleOnTertiary = Color(0xFFFFFFFF)
val purpleTertiaryContainer = Color(0xFFFFD8E4)
val purpleOnTertiaryContainer = Color(0xFF31111D)

val purpleSurface = Color(0xFFFEF7FF)
val purpleOnSurface = Color(0xFF1D1B20)
val purpleSurfaceContainerLowest = Color(0xFFFFFFFF)
val purpleSurfaceContainerLow = Color(0xFFF7F2FA)
val purpleSurfaceContainer = Color(0xFFF3EDF7)
val purpleSurfaceContainerHigh = Color(0xFFECE6F0)
val purpleSurfaceContainerHighest = Color(0xFFE6E0E9)

val purpleSurfaceVariant = Color(0xFFE7E0EC)
val purpleOnSurfaceVariant = Color(0xFF49454F)
val purpleOutline = Color(0xFF79747E)
val purpleOutlineVariant = Color(0xFFCAC4D0)

val purpleInverseSurface = Color(0xFF322F35)
val purpleInverseOnSurface = Color(0xFFF5EFF7)
val purpleInversePrimary = Color(0xFFD0BCFF)

val purpleError = Color(0xFFB3261E)
val purpleOnError = Color(0xFFFFFFFF)
val purpleErrorContainer = Color(0xFFF9DEDC)
val purpleOnErrorContainer = Color(0xFF410E0B)

val PurpleColorScheme: ColorScheme = lightColorScheme(
    primary = purplePrimary,
    onPrimary = purpleOnPrimary,
    primaryContainer = purplePrimaryContainer,
    onPrimaryContainer = purpleOnPrimaryContainer,
    secondary = purpleSecondary,
    onSecondary = purpleOnSecondary,
    secondaryContainer = purpleSecondaryContainer,
    onSecondaryContainer = purpleOnSecondaryContainer,
    tertiary = purpleTertiary,
    onTertiary = purpleOnTertiary,
    tertiaryContainer = purpleTertiaryContainer,
    onTertiaryContainer = purpleOnTertiaryContainer,
    background = purpleSurface,
    onBackground = purpleOnSurface,
    surface = purpleSurface,
    onSurface = purpleOnSurface,
    surfaceVariant = purpleSurfaceVariant,
    onSurfaceVariant = purpleOnSurfaceVariant,
    surfaceContainerLowest = purpleSurfaceContainerLowest,
    surfaceContainerLow = purpleSurfaceContainerLow,
    surfaceContainer = purpleSurfaceContainer,
    surfaceContainerHigh = purpleSurfaceContainerHigh,
    surfaceContainerHighest = purpleSurfaceContainerHighest,
    outline = purpleOutline,
    outlineVariant = purpleOutlineVariant,
    inverseSurface = purpleInverseSurface,
    inverseOnSurface = purpleInverseOnSurface,
    inversePrimary = purpleInversePrimary,
    error = purpleError,
    onError = purpleOnError,
    errorContainer = purpleErrorContainer,
    onErrorContainer = purpleOnErrorContainer
)

// 2. Ocean Blue Theme
val BlueColorScheme: ColorScheme = lightColorScheme(
    primary = Color(0xFF0061A4),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD1E4FF),
    onPrimaryContainer = Color(0xFF001D36),
    secondary = Color(0xFF535F70),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD7E3F7),
    onSecondaryContainer = Color(0xFF101C2B),
    tertiary = Color(0xFF6B5778),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFF2DAFF),
    onTertiaryContainer = Color(0xFF251432),
    background = Color(0xFFFDFBFF),
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFFDFBFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFDFE2EB),
    onSurfaceVariant = Color(0xFF43474E),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF3F3FA),
    surfaceContainer = Color(0xFFEDEEF6),
    surfaceContainerHigh = Color(0xFFE7E8F0),
    surfaceContainerHighest = Color(0xFFE1E2EA),
    outline = Color(0xFF73777F),
    outlineVariant = Color(0xFFC3C7D0),
    inverseSurface = Color(0xFF2F3033),
    inverseOnSurface = Color(0xFFF1F0F4),
    inversePrimary = Color(0xFF9ECAFF),
    error = purpleError,
    onError = purpleOnError,
    errorContainer = purpleErrorContainer,
    onErrorContainer = purpleOnErrorContainer
)

// 3. Botanical Emerald Green Theme
val EmeraldColorScheme: ColorScheme = lightColorScheme(
    primary = Color(0xFF006C4C),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF8DF7C8),
    onPrimaryContainer = Color(0xFF002114),
    secondary = Color(0xFF4D6357),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCFE9D8),
    onSecondaryContainer = Color(0xFF0A1F16),
    tertiary = Color(0xFF3D6373),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFC1E8FC),
    onTertiaryContainer = Color(0xFF001F2A),
    background = Color(0xFFFBFDF9),
    onBackground = Color(0xFF191C1A),
    surface = Color(0xFFFBFDF9),
    onSurface = Color(0xFF191C1A),
    surfaceVariant = Color(0xFFDBE5DE),
    onSurfaceVariant = Color(0xFF404944),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF2F5F0),
    surfaceContainer = Color(0xFFECEFEA),
    surfaceContainerHigh = Color(0xFFE6E9E4),
    surfaceContainerHighest = Color(0xFFE0E3DE),
    outline = Color(0xFF707973),
    outlineVariant = Color(0xFFBFC9C2),
    inverseSurface = Color(0xFF2E312F),
    inverseOnSurface = Color(0xFFF0F1ED),
    inversePrimary = Color(0xFF70DAAD),
    error = purpleError,
    onError = purpleOnError,
    errorContainer = purpleErrorContainer,
    onErrorContainer = purpleOnErrorContainer
)

// 4. Berry Rose Theme
val RoseColorScheme: ColorScheme = lightColorScheme(
    primary = Color(0xFF9C4146),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDADA),
    onPrimaryContainer = Color(0xFF40000A),
    secondary = Color(0xFF775656),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDAD9),
    onSecondaryContainer = Color(0xFF2C1516),
    tertiary = Color(0xFF755A2F),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDDB3),
    onTertiaryContainer = Color(0xFF291800),
    background = Color(0xFFFFFBFA),
    onBackground = Color(0xFF201A1A),
    surface = Color(0xFFFFFBFA),
    onSurface = Color(0xFF201A1A),
    surfaceVariant = Color(0xFFF5DDDB),
    onSurfaceVariant = Color(0xFF534342),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFBF1F0),
    surfaceContainer = Color(0xFFF5EBEA),
    surfaceContainerHigh = Color(0xFFEFE5E4),
    surfaceContainerHighest = Color(0xFFE9DFDE),
    outline = Color(0xFF857371),
    outlineVariant = Color(0xFFD8C2BF),
    inverseSurface = Color(0xFF362F2F),
    inverseOnSurface = Color(0xFFFBEEED),
    inversePrimary = Color(0xFFFFB3B4),
    error = purpleError,
    onError = purpleOnError,
    errorContainer = purpleErrorContainer,
    onErrorContainer = purpleOnErrorContainer
)

// 5. Warm Amber Theme
val AmberColorScheme: ColorScheme = lightColorScheme(
    primary = Color(0xFF865300),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDDB8),
    onPrimaryContainer = Color(0xFF2B1700),
    secondary = Color(0xFF705B41),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFCE0BE),
    onSecondaryContainer = Color(0xFF281805),
    tertiary = Color(0xFF53643E),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFD6E9B9),
    onTertiaryContainer = Color(0xFF121F03),
    background = Color(0xFFFFF8F4),
    onBackground = Color(0xFF211A13),
    surface = Color(0xFFFFF8F4),
    onSurface = Color(0xFF211A13),
    surfaceVariant = Color(0xFFF0E0D0),
    onSurfaceVariant = Color(0xFF504539),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFBF1E8),
    surfaceContainer = Color(0xFFF5EBE1),
    surfaceContainerHigh = Color(0xFFEFE5DC),
    surfaceContainerHighest = Color(0xFFE9DFD6),
    outline = Color(0xFF827568),
    outlineVariant = Color(0xFFD4C4B5),
    inverseSurface = Color(0xFF372F27),
    inverseOnSurface = Color(0xFFFCEEE4),
    inversePrimary = Color(0xFFFFB95E),
    error = purpleError,
    onError = purpleOnError,
    errorContainer = purpleErrorContainer,
    onErrorContainer = purpleOnErrorContainer
)

// ==================== DARK COLOR SCHEMES ====================

// 1. Purple Dark
val PurpleDarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF492532),
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),
    background = Color(0xFF141218),
    onBackground = Color(0xFFE6E0E9),
    surface = Color(0xFF141218),
    onSurface = Color(0xFFE6E0E9),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceContainerLowest = Color(0xFF0F0D13),
    surfaceContainerLow = Color(0xFF1D1B20),
    surfaceContainer = Color(0xFF211F26),
    surfaceContainerHigh = Color(0xFF2B2930),
    surfaceContainerHighest = Color(0xFF36343B),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    inverseSurface = Color(0xFFE6E0E9),
    inverseOnSurface = Color(0xFF322F35),
    inversePrimary = purplePrimary,
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC)
)

val PurpleAmoledColorScheme: ColorScheme = PurpleDarkColorScheme.copy(
    background = Color(0xFF000000),
    surface = Color(0xFF000000),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0B0B0E),
    surfaceContainer = Color(0xFF131217),
    surfaceContainerHigh = Color(0xFF1C1A21),
    surfaceContainerHighest = Color(0xFF26242D)
)

// 2. Ocean Blue Dark
val BlueDarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFF9ECAFF),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF00497D),
    onPrimaryContainer = Color(0xFFD1E4FF),
    secondary = Color(0xFFBBC7DB),
    onSecondary = Color(0xFF253140),
    secondaryContainer = Color(0xFF3B4858),
    onSecondaryContainer = Color(0xFFD7E3F7),
    tertiary = Color(0xFFD6BEE4),
    onTertiary = Color(0xFF3B2948),
    tertiaryContainer = Color(0xFF523F5F),
    onTertiaryContainer = Color(0xFFF2DAFF),
    background = Color(0xFF111418),
    onBackground = Color(0xFFE2E2E6),
    surface = Color(0xFF111418),
    onSurface = Color(0xFFE2E2E6),
    surfaceVariant = Color(0xFF43474E),
    onSurfaceVariant = Color(0xFFC3C7D0),
    surfaceContainerLowest = Color(0xFF0C0E12),
    surfaceContainerLow = Color(0xFF191C20),
    surfaceContainer = Color(0xFF1D2024),
    surfaceContainerHigh = Color(0xFF282A2F),
    surfaceContainerHighest = Color(0xFF33353A),
    outline = Color(0xFF8D9199),
    outlineVariant = Color(0xFF43474E),
    inverseSurface = Color(0xFFE2E2E6),
    inverseOnSurface = Color(0xFF2F3033),
    inversePrimary = Color(0xFF0061A4),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC)
)

val BlueAmoledColorScheme: ColorScheme = BlueDarkColorScheme.copy(
    background = Color(0xFF000000),
    surface = Color(0xFF000000),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0A0C10),
    surfaceContainer = Color(0xFF10141A),
    surfaceContainerHigh = Color(0xFF171B23),
    surfaceContainerHighest = Color(0xFF212630)
)

// 3. Emerald Dark
val EmeraldDarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFF70DAAD),
    onPrimary = Color(0xFF003825),
    primaryContainer = Color(0xFF005238),
    onPrimaryContainer = Color(0xFF8DF7C8),
    secondary = Color(0xFFB4CCBC),
    onSecondary = Color(0xFF1F352A),
    secondaryContainer = Color(0xFF354B3F),
    onSecondaryContainer = Color(0xFFCFE9D8),
    tertiary = Color(0xFFA5CDDE),
    onTertiary = Color(0xFF063544),
    tertiaryContainer = Color(0xFF234C5B),
    onTertiaryContainer = Color(0xFFC1E8FC),
    background = Color(0xFF0F1512),
    onBackground = Color(0xFFDEE4DF),
    surface = Color(0xFF0F1512),
    onSurface = Color(0xFFDEE4DF),
    surfaceVariant = Color(0xFF404944),
    onSurfaceVariant = Color(0xFFBFC9C2),
    surfaceContainerLowest = Color(0xFF0A0F0D),
    surfaceContainerLow = Color(0xFF171D1A),
    surfaceContainer = Color(0xFF1B211E),
    surfaceContainerHigh = Color(0xFF252C28),
    surfaceContainerHighest = Color(0xFF303733),
    outline = Color(0xFF8A938C),
    outlineVariant = Color(0xFF404944),
    inverseSurface = Color(0xFFDEE4DF),
    inverseOnSurface = Color(0xFF2E312F),
    inversePrimary = Color(0xFF006C4C),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC)
)

val EmeraldAmoledColorScheme: ColorScheme = EmeraldDarkColorScheme.copy(
    background = Color(0xFF000000),
    surface = Color(0xFF000000),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0A0E0C),
    surfaceContainer = Color(0xFF101613),
    surfaceContainerHigh = Color(0xFF18201B),
    surfaceContainerHighest = Color(0xFF222B25)
)

// 4. Berry Rose Dark
val RoseDarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFFFFB3B4),
    onPrimary = Color(0xFF5F121C),
    primaryContainer = Color(0xFF7D2930),
    onPrimaryContainer = Color(0xFFFFDADA),
    secondary = Color(0xFFE7BDBE),
    onSecondary = Color(0xFF44292B),
    secondaryContainer = Color(0xFF5D3F41),
    onSecondaryContainer = Color(0xFFFFDAD9),
    tertiary = Color(0xFFE5C18D),
    onTertiary = Color(0xFF422C05),
    tertiaryContainer = Color(0xFF5B4219),
    onTertiaryContainer = Color(0xFFFFDDB3),
    background = Color(0xFF1A1112),
    onBackground = Color(0xFFF1DFE0),
    surface = Color(0xFF1A1112),
    onSurface = Color(0xFFF1DFE0),
    surfaceVariant = Color(0xFF534342),
    onSurfaceVariant = Color(0xFFD8C2BF),
    surfaceContainerLowest = Color(0xFF140C0D),
    surfaceContainerLow = Color(0xFF23191A),
    surfaceContainer = Color(0xFF271D1E),
    surfaceContainerHigh = Color(0xFF322829),
    surfaceContainerHighest = Color(0xFF3E3233),
    outline = Color(0xFFA08C8A),
    outlineVariant = Color(0xFF534342),
    inverseSurface = Color(0xFFF1DFE0),
    inverseOnSurface = Color(0xFF362F2F),
    inversePrimary = Color(0xFF9C4146),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC)
)

val RoseAmoledColorScheme: ColorScheme = RoseDarkColorScheme.copy(
    background = Color(0xFF000000),
    surface = Color(0xFF000000),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0F0A0B),
    surfaceContainer = Color(0xFF171011),
    surfaceContainerHigh = Color(0xFF221719),
    surfaceContainerHighest = Color(0xFF2D1F21)
)

// 5. Warm Amber Dark
val AmberDarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFFFFB95E),
    onPrimary = Color(0xFF472A00),
    primaryContainer = Color(0xFF653E00),
    onPrimaryContainer = Color(0xFFFFDDB8),
    secondary = Color(0xFFDEC2A2),
    onSecondary = Color(0xFF3E2D16),
    secondaryContainer = Color(0xFF56432B),
    onSecondaryContainer = Color(0xFFFCE0BE),
    tertiary = Color(0xFFBACDA0),
    onTertiary = Color(0xFF263514),
    tertiaryContainer = Color(0xFF3C4C29),
    onTertiaryContainer = Color(0xFFD6E9B9),
    background = Color(0xFF18120C),
    onBackground = Color(0xFFEFE0D5),
    surface = Color(0xFF18120C),
    onSurface = Color(0xFFEFE0D5),
    surfaceVariant = Color(0xFF504539),
    onSurfaceVariant = Color(0xFFD4C4B5),
    surfaceContainerLowest = Color(0xFF130D08),
    surfaceContainerLow = Color(0xFF211A13),
    surfaceContainer = Color(0xFF251F18),
    surfaceContainerHigh = Color(0xFF302922),
    surfaceContainerHighest = Color(0xFF3C342C),
    outline = Color(0xFF9C8F80),
    outlineVariant = Color(0xFF504539),
    inverseSurface = Color(0xFFEFE0D5),
    inverseOnSurface = Color(0xFF372F27),
    inversePrimary = Color(0xFF865300),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC)
)

val AmberAmoledColorScheme: ColorScheme = AmberDarkColorScheme.copy(
    background = Color(0xFF000000),
    surface = Color(0xFF000000),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0E0B08),
    surfaceContainer = Color(0xFF16120D),
    surfaceContainerHigh = Color(0xFF211B14),
    surfaceContainerHighest = Color(0xFF2D251C)
)

fun getAppColorScheme(
    palette: AppThemePalette,
    isDark: Boolean = false,
    isAmoled: Boolean = false
): ColorScheme {
    return if (isAmoled) {
        when (palette) {
            AppThemePalette.PURPLE -> PurpleAmoledColorScheme
            AppThemePalette.OCEAN_BLUE -> BlueAmoledColorScheme
            AppThemePalette.EMERALD -> EmeraldAmoledColorScheme
            AppThemePalette.ROSE -> RoseAmoledColorScheme
            AppThemePalette.AMBER -> AmberAmoledColorScheme
        }
    } else if (isDark) {
        when (palette) {
            AppThemePalette.PURPLE -> PurpleDarkColorScheme
            AppThemePalette.OCEAN_BLUE -> BlueDarkColorScheme
            AppThemePalette.EMERALD -> EmeraldDarkColorScheme
            AppThemePalette.ROSE -> RoseDarkColorScheme
            AppThemePalette.AMBER -> AmberDarkColorScheme
        }
    } else {
        when (palette) {
            AppThemePalette.PURPLE -> PurpleColorScheme
            AppThemePalette.OCEAN_BLUE -> BlueColorScheme
            AppThemePalette.EMERALD -> EmeraldColorScheme
            AppThemePalette.ROSE -> RoseColorScheme
            AppThemePalette.AMBER -> AmberColorScheme
        }
    }
}
