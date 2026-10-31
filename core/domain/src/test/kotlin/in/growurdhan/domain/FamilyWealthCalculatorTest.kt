package `in`.growurdhan.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

/**
 * Fresh tests for [FamilyWealthCalculator]. Values are hand-computed:
 * scale is 10,000 minor units per rupee, so 1,500,000 minor units is
 * Rs 150, and 25,000,000 minor units is Rs 2,500.
 */
class FamilyWealthCalculatorTest {

    @Test
    fun totalsAcrossMembersAndCategories() {
        val wealth = FamilyWealthCalculator.summarize(
            listOf(
                HoldingValue("h1", "meera", "MUTUAL_FUND", 25_000_000L),
                HoldingValue("h2", "meera", "FIXED_INCOME", 10_000_000L),
                HoldingValue("h3", "arjun", "GOLD_SGB", 5_000_000L),
            ),
        )
        // Rs 2,500 + Rs 1,000 + Rs 500 = Rs 4,000.
        assertEquals("₹4,000.00", wealth.totalWealthText)
        assertEquals(2, wealth.memberCount)
        assertEquals(3, wealth.holdingCount)
        assertEquals(
            listOf(
                CategoryTotal("Mutual funds", "₹2,500.00"),
                CategoryTotal("Gold and SGB", "₹500.00"),
                CategoryTotal("Fixed income", "₹1,000.00"),
            ),
            wealth.sections,
        )
    }

    @Test
    fun liabilitiesReduceTotalWealth() {
        val wealth = FamilyWealthCalculator.summarize(
            listOf(
                HoldingValue("h1", "meera", "REAL_ESTATE", 500_000_000L),
                HoldingValue("h2", "meera", "LIABILITY", -200_000_000L),
            ),
        )
        // Rs 50,000 - Rs 20,000 = Rs 30,000.
        assertEquals("₹30,000.00", wealth.totalWealthText)
        assertEquals(
            listOf(
                CategoryTotal("Real estate", "₹50,000.00"),
                CategoryTotal("Loans", "−₹20,000.00"),
            ),
            wealth.sections,
        )
    }

    @Test
    fun emptyFamilySummarizesToZero() {
        val wealth = FamilyWealthCalculator.summarize(emptyList())
        assertEquals("₹0.00", wealth.totalWealthText)
        assertEquals(0, wealth.memberCount)
        assertEquals(0, wealth.holdingCount)
        assertEquals(emptyList<CategoryTotal>(), wealth.sections)
    }

    @Test
    fun unknownCategoryIsRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            FamilyWealthCalculator.summarize(
                listOf(HoldingValue("h1", "meera", "CRYPTO", 1_000_000L)),
            )
        }
    }
}
