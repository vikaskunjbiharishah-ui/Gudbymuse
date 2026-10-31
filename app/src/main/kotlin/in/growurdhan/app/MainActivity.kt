package `in`.growurdhan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import `in`.growurdhan.app.navigation.GudNavHost
import `in`.growurdhan.app.ui.theme.GudTheme

/**
 * The single Activity. It holds no business state, no money, and no
 * navigation state of its own. State lives in ViewModels, navigation
 * lives in the Navigation component, and data lives in the domain layer.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GudTheme {
                GudNavHost()
            }
        }
    }
}
