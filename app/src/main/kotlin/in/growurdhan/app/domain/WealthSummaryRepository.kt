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
 * is [DomainWealthSummaryRepository], which runs the domain calculator
 * over holdings supplied by a [HoldingsSource].
 */
interface WealthSummaryRepository {
    suspend fun load(): Result<FamilyWealthSummary>
}
