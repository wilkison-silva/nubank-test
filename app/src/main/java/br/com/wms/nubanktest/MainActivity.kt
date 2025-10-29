package br.com.wms.nubanktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.wms.linkshortener.presentation.screen.AddLinkShortenerScreen
import br.com.wms.nubanktest.ui.theme.NubankTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NubankTestTheme {
                AddLinkShortenerScreen()
            }
        }
    }
}
