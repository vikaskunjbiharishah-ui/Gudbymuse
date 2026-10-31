package `in`.growurdhan.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import `in`.growurdhan.app.domain.FamilyWealthSummary
import `in`.growurdhan.app.domain.WealthSummaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Ready(val summary: FamilyWealthSummary) : HomeUiState
    data class Unavailable(val reason: String) : HomeUiState
}

/**
 * Home screen state only. No business logic lives in the Activity, and
 * no money is computed here. A failed load becomes [HomeUiState.Unavailable],
 * never a fabricated total.
 */
class HomeViewModel(
    private val repository: WealthSummaryRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            _uiState.value = repository.load().fold(
                onSuccess = { HomeUiState.Ready(it) },
                onFailure = {
                    HomeUiState.Unavailable(
                        it.message ?: "Wealth data is not available yet.",
                    )
                },
            )
        }
    }

    companion object {
        fun factory(
            repository: WealthSummaryRepository,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    require(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                        "Unknown ViewModel class"
                    }
                    return HomeViewModel(repository) as T
                }
            }
    }
}
