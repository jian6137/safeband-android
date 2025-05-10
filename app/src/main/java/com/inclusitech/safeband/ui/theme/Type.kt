package com.inclusitech.safeband.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import com.inclusitech.safeband.R

//configure the proper font weights according to material 3
val ttf = arrayOf(
    Font(R.font.hind_regular),
    Font(R.font.hind_semibold, FontWeight.SemiBold),
    Font(R.font.hind_medium, FontWeight.Medium),
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_medium, FontWeight.Medium)
)

val bodyFontFamily = FontFamily(
    ttf[0],ttf[1],ttf[2]
)
val displayFontFamily = FontFamily(
    ttf[3],ttf[4],ttf[5]
)

val baseline = Typography()

val SafeBandTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily, fontWeight = FontWeight.Bold),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily, fontWeight = FontWeight.Bold),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily, fontWeight = FontWeight.Bold),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily, fontWeight = FontWeight.SemiBold),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily, fontWeight = FontWeight.SemiBold),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily, fontWeight = FontWeight.SemiBold),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily, fontWeight = FontWeight.Medium),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily, fontWeight = FontWeight.Medium),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily, fontWeight = FontWeight.Medium),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.Medium),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)