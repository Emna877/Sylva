package com.example.sylva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sylva.ui.SylvaApp
import com.example.sylva.ui.theme.SylvaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SylvaTheme {
                SylvaApp()
            }
        }
    }
}
