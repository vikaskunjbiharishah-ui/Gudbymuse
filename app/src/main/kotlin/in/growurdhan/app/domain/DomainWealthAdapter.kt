package `in`.growurdhan.app.domain

import `in`.growurdhan.domain.FamilyWealthCalculator
import `in`.growurdhan.domain.HoldingValue

/**
 * Supplies holding values to the summary calculator. The vault-backed
 * implementation comes later; until then the only source is
 * [UnconnectedHoldingsSource], which fails closed.
 */
interface HoldingsSource {
    fun holdings(): Result<List<HoldingValue>>
}

class UnconnectedHoldingsSource : HoldingsSource {
    override fun holdings(): Result<List<HoldingValue>> =
        Result.failure(
            IllegalStateException("Wealth data is not connected yet."),
        )
}

/**
 * Connects the home screen to the real domain calculator. It adds no
 * money logic of its own: it only converts the domain summary into
 * the screen's summary type.
 */
class DomainWealthSummaryRepository(
    private val source: HoldingsSource,
) : WealthSummaryRepository {
    override suspend fun load(): Result<FamilyWealthSummary> =
        runCatching {
            val holdings = source.holdings().getOrThrow()
            val wealth = FamilyWealthCalculator.summarize(holdings)
            FamilyWealthSummary(
                totalWealthText = wealth.totalWealthText,
                memberCount = wealth.memberCount,
                holdingCount = wealth.holdingCount,
                sections = wealth.sections.map { section ->
                    WealthSection(section.title, section.valueText)
                },
            )
        }
}
