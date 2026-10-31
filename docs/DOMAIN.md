# Domain module — ported money and portfolio core

Module: `:core:domain` (pure Kotlin/JVM, no Android dependency).
Branch: `shell/navigation-design-system`
Status: **ported, not yet compiled or tested here.** There is no Java
on the builder machine, so no local build or test run was possible.
Verification waits on the same CI workflows permission described in
`docs/SHELL.md`. A test that has not run is not a pass.

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

Algorithms were not edited during the port. If a ported algorithm
ever needs a fix, the fix comes with a new test and a note here,
not a silent edit.

## Tests ported with the code

- `NativePortfolioMathTest`, `NativeTaxLotTraceTest`,
  `NativeMoneyWeightedReturnTest` (ported, package renamed).
- `DomainSmokeTest` (fresh): money addition, lakh grouping
  (₹12,345.68), and April-to-March assessment years.

## What is NOT here yet

- The app module does not depend on `:core:domain` yet. Wiring the
  home screen's `WealthSummaryRepository` to this module, plus the
  vault, the CAS parser, and prices, is the next step after the
  module's own tests go green in CI.
- No secrets, no keystore, no statement data, and no user data were
  copied. The module contains only logic and tests.
