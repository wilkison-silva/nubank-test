package br.com.wms.nubanktest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import br.com.wms.nubanktest.R

private val LatoFamily =
    FontFamily(
        Font(R.font.lato, weight = FontWeight.Normal, style = FontStyle.Normal),
        Font(R.font.lato_black, weight = FontWeight.Black, style = FontStyle.Normal),
        Font(R.font.lato_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
        Font(R.font.lato_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
        Font(R.font.lato_black_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
        Font(R.font.lato_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
        Font(R.font.lato_light, weight = FontWeight.Light, style = FontStyle.Normal),
        Font(R.font.lato_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
        Font(R.font.lato_thin, weight = FontWeight.Thin, style = FontStyle.Normal),
        Font(R.font.lato_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
    )

private val defaultTypography = Typography()
val Typography =
    Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = LatoFamily),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = LatoFamily),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = LatoFamily),
        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = LatoFamily),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = LatoFamily),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = LatoFamily),
        titleLarge = defaultTypography.titleLarge.copy(fontFamily = LatoFamily),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = LatoFamily),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = LatoFamily),
        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = LatoFamily),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = LatoFamily),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = LatoFamily),
        labelLarge = defaultTypography.labelLarge.copy(fontFamily = LatoFamily),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = LatoFamily),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = LatoFamily),
    )
