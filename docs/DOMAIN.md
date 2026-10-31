# Domain module — ported money and portfolio core

Module: `:core:domain` (pure Kotlin/JVM, no Android dependency).
Branch: `shell/navigation-design-system`
Status: **compiled and tested locally on 2026-10-31: 40 tests pass.**
Toolchain: Temurin 17.0.21 + Kotlin compiler 2.0.21 + JUnit 4.13.2,
run directly against the module sources (Gradle was not used, because
no Gradle distribution is installed here). One compiler warning
remains: the ported `ScaledAmount` internal constructor and its
generated `copy()` method, which becomes an error in Kotlin 2.1 and
must be fixed before any Kotlin upgrade. The Android app build and
CI verification still wait on the workflows permission described in
`docs/SHELL.md`.

## What was ported, as-is

From the earlier Kotlin work's `core/model`, with only the package
renamed to `in.growurdhan.domain`:

- `ScaledAmount` — signed scaled integers, 10,000 minor units per
  rupee, 100,000,000 per whole quantity unit. No floating-point money.
- `NativePortfolioMath` — append-only event ledger and the 8-category
  Indian investment taxonomy (equity, mutual fund, gold/SGB, fixed
  income, real estate, specialised, cash/bank, liability).
- `NativeMoneyWeightedReturn` — money-weighted return (XIRR). Doubles
  exist only inside the solver; stored values stay scaled integers.
  Missing or invalid flows yield N/A, never an invented return.
- `NativeTaxLotTrace` — FIFO tax-lot tracing, Indian April-to-March
  assessment years, and the grandfathered-cost source formula. It
  never invents a market value, a tax rate, or an exemption.
- `InrFormat` — Indian digit grouping for display only. Display
  rounding never changes the stored amount.
- `NativeExposureAllocation` — category exposure allocation, added
  because the ported portfolio tests depend on it. Same port rules:
  logic as-is, package renamed only.

Algorithms were not edited during the port. If a ported algorithm
ever needs a fix, the fix comes with a new test and a note here,
not a silent edit.

## Tests ported with the code

- `NativePortfolioMathTest`, `NativeTaxLotTraceTest`,
  `NativeMoneyWeightedReturnTest` (ported, package renamed).
- `DomainSmokeTest` (fresh): money addition, lakh grouping
  (₹12,345.68), and April-to-March assessment years.

## New code in this module: family wealth summary

- `FamilyWealthCalculator` (fresh, not ported): takes a list of
  `HoldingValue` records and returns total wealth, member count,
  holding count, and per-category totals, all formatted with
  `InrFormat`. Liabilities are stored negative, so loans reduce
  total wealth. Unknown categories are rejected, and overflow
  fails loudly via exact addition. It fetches no prices and reads
  no database; the data layer supplies the holding values later.
- `FamilyWealthCalculatorTest` (fresh): totals across members and
  categories, loans reducing wealth, an empty family, and rejection
  of an unknown category. Values are hand-computed in the test.

## What is NOT here yet

- The app module does not depend on `:core:domain` yet. Wiring the
  home screen's `WealthSummaryRepository` to this calculator, plus
  the vault, the CAS parser, and prices, is the next step after the
  module's own tests go green.
- No secrets, no keystore, no statement data, and no user data were
  copied. The module contains only logic and tests.
