package `in`.growurdhan.domain

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Fresh smoke tests for the ported domain module. These check the
 * behaviours a normal user actually sees: money addition, Indian
 * digit grouping, and the April-to-March assessment year.
 */
class DomainSmokeTest {

    @Test
    fun moneyAddsInScaledMinorUnits() {
        val sum = ScaledAmount.moneyMinor(10_000L) +
            ScaledAmount.moneyMinor(5_000L)
        assertEquals(15_000L, sum.minor)
    }

    @Test
    fun indianGroupingUsesLakhGrouping() {
        assertEquals("₹12,345.68", InrFormat.formatOverview(123_456_789L))
        assertEquals("₹100.00", InrFormat.formatOverview(1_000_000L))
    }

    @Test
    fun assessmentYearFollowsAprilToMarch() {
        assertEquals(
            "AY27-28",
            NativeTaxLotTrace.assessmentYear(LocalDate.of(2026, 5, 10)),
        )
        assertEquals(
            "AY26-27",
            NativeTaxLotTrace.assessmentYear(LocalDate.of(2026, 3, 10)),
        )
    }
}
