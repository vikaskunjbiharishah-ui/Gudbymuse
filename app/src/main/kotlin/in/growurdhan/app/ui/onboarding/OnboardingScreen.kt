package `in`.growurdhan.app.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.growurdhan.app.ui.theme.GudPrimaryButton
import `in`.growurdhan.app.ui.theme.GudSectionTitle

/**
 * First-run screens in plain language. No finance words are used without
 * an explanation, and nothing promises a feature that is not built yet.
 */
@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "GrowUrDhan",
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = "Your whole family's money, in one place, on this phone.",
            style = MaterialTheme.typography.bodyLarge,
        )

        GudSectionTitle("Your money stays on this phone")
        Text(
            text = "There is no online account and no server copy of your " +
                "finances. Your statements and holdings live only on " +
                "this device, behind your phone lock.",
            style = MaterialTheme.typography.bodyLarge,
        )

        GudSectionTitle("Built for a normal Indian family")
        Text(
            text = "Mutual funds, bank deposits, PPF, gold, property, " +
                "loans, and goals — for every family member, in Hindi " +
                "and English.",
            style = MaterialTheme.typography.bodyLarge,
        )

        GudSectionTitle("One honest note about backup")
        Text(
            text = "Safe backup and restore is being built. Until it " +
                "arrives, losing this phone means losing the data in " +
                "this app. Please do not uninstall the app until backup " +
                "is ready.",
            style = MaterialTheme.typography.bodyLarge,
        )

        GudPrimaryButton(
            text = "Continue",
            onClick = onFinished,
        )
    }
}
