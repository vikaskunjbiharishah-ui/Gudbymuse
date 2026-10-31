package `in`.growurdhan.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.growurdhan.app.ui.theme.GudCard
import `in`.growurdhan.app.ui.theme.GudMoneyText
import `in`.growurdhan.app.ui.theme.GudPrimaryButton
import `in`.growurdhan.app.ui.theme.GudSectionTitle

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    when (val state = uiState) {
        is HomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
        is HomeUiState.Ready -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    GudSectionTitle("Total family wealth")
                    GudMoneyText(state.summary.totalWealthText)
                    Text(
                        text = "${state.summary.memberCount} family members · " +
                            "${state.summary.holdingCount} holdings",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                items(state.summary.sections) { section ->
                    GudCard {
                        GudSectionTitle(section.title)
                        GudMoneyText(section.valueText)
                    }
                }
            }
        }
        is HomeUiState.Unavailable -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                GudSectionTitle("Wealth data is not available yet")
                Text(
                    text = state.reason,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                )
                GudPrimaryButton(
                    text = "Try again",
                    onClick = { viewModel.refresh() },
                )
            }
        }
    }
}
