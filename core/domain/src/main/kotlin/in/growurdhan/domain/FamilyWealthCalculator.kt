package `in`.growurdhan.domain

/**
 * A single holding's current value, as stored by the data layer.
 * [valueMinor] is a scaled-integer money amount (10,000 minor units
 * per rupee, see [ScaledAmount]). [category] must be one of the eight
 * categories in [NativeAssetTaxonomy]. Liabilities are stored as
 * negative values, so a home loan reduces total wealth honestly.
 */
data class HoldingValue(
    val holdingId: String,
    val ownerId: String,
    val category: String,
    val valueMinor: Long,
)

/** One category total, formatted for display by this calculator only. */
data class CategoryTotal(
    val title: String,
    val valueText: String,
)

/** A family's wealth, formatted for display by this calculator only. */
data class FamilyWealth(
    val totalWealthText: String,
    val memberCount: Int,
    val holdingCount: Int,
    val sections: List<CategoryTotal>,
)

/**
 * Pure summary calculation: holdings in, formatted family wealth out.
 * No database, no network, no prices are fetched here. Unknown
 * categories are rejected rather than silently grouped, and arithmetic
 * uses exact addition so an overflow fails loudly instead of wrapping.
 */
object FamilyWealthCalculator {

    fun summarize(holdings: List<HoldingValue>): FamilyWealth {
        holdings.forEach { holding ->
            require(holding.category in NativeAssetTaxonomy.subcategories) {
                "Unsupported asset category: ${holding.category}"
            }
        }
        var total = 0L
        val byCategory = linkedMapOf<String, Long>()
        holdings.forEach { holding ->
            total = Math.addExact(total, holding.valueMinor)
            byCategory[holding.category] =
                Math.addExact(byCategory[holding.category] ?: 0L, holding.valueMinor)
        }
        val sections = NativeAssetTaxonomy.subcategories.keys.mapNotNull { category ->
            val value = byCategory[category] ?: return@mapNotNull null
            CategoryTotal(
                title = categoryDisplayName(category),
                valueText = InrFormat.formatOverview(value),
            )
        }
        return FamilyWealth(
            totalWealthText = InrFormat.formatOverview(total),
            memberCount = holdings.map { it.ownerId }.distinct().size,
            holdingCount = holdings.size,
            sections = sections,
        )
    }

    private fun categoryDisplayName(category: String): String = when (category) {
        "EQUITY" -> "Equity"
        "MUTUAL_FUND" -> "Mutual funds"
        "GOLD_SGB" -> "Gold and SGB"
        "FIXED_INCOME" -> "Fixed income"
        "REAL_ESTATE" -> "Real estate"
        "SPECIALISED" -> "Specialised"
        "CASH_BANK" -> "Cash and bank"
        "LIABILITY" -> "Loans"
        else -> category
    }
}
