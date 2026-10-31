package `in`.growurdhan.app.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * The only button styles in the app. Every tappable primary action is at
 * least 48dp tall, so it is usable on a phone without precise tapping.
 */
@Composable
fun GudPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
    ) {
        Text(text)
    }
}

@Composable
fun GudOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
    ) {
        Text(text)
    }
}

@Composable
fun GudSectionTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier,
    )
}

@Composable
fun GudCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

/**
 * Displays a money value that was already formatted by the domain layer.
 * The shell never formats money itself and never invents a value.
 */
@Composable
fun GudMoneyText(
    formatted: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = formatted,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier.semantics { contentDescription = formatted },
    )
}

/**
 * Honest placeholder for a section that is not built yet. It shows words,
 * never fake numbers or fake buttons.
 */
@Composable
fun GudComingSoonScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            GudSectionTitle(title)
            Text(
                text = "This section is being built.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
