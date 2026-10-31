package `in`.growurdhan.app.domain

/**
 * A family's wealth summary, already formatted for display by the domain
 * layer. The UI shell never computes money, prices, or returns itself.
 */
data class WealthSection(
    val title: String,
    val valueText: String,
)

data class FamilyWealthSummary(
    val totalWealthText: String,
    val memberCount: Int,
    val holdingCount: Int,
    val sections: List<WealthSection>,
)

/**
 * The only contract the home screen depends on. The real implementation
 * comes from the ported domain core. Until then, the app wires
 * [UnconnectedWealthSummaryRepository], which fails closed with an
 * honest message instead of showing invented numbers.
 */
interface WealthSummaryRepository {
    suspend fun load(): Result<FamilyWealthSummary>
}

class UnconnectedWealthSummaryRepository : WealthSummaryRepository {
    override suspend fun load(): Result<FamilyWealthSummary> =
        Result.failure(
            IllegalStateException("Wealth data is not connected yet."),
        )
}
