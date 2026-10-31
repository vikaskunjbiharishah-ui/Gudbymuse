# Core Inventory — what is kept, rebuilt, or left out

Source checked: the Kotlin/Jetpack branch of the earlier project
(`android-native/` only). Counts below are from that source on 2026-10-31.

## Scope: what is IN and what is OUT

- IN: `android-native/app` (137 Kotlin files), `core/database` (35),
  `core/model` (61), `core/designsystem` (2), `core/parser-api` (2).
  Total 237 Kotlin files, plus 49 unit tests and 49 instrumented tests.
- OUT, never copied: the root web/Tauri scaffolding (`package.json`,
  `vite.config.ts`, `public/`, `src-tauri/`, `src/`, `docs/`,
  `marketing/`, `index.html`) and the old documentation, which describes
  the wrong branch and app id. Most importantly, the committed debug
  keystore under `src-tauri/keystore/` is excluded entirely.

## KEEP — proven domain core (protect, do not rewrite from zero)

- Money: `ScaledAmount` — signed scaled integers, 10,000 minor units
  per rupee, 100,000,000 per whole quantity unit. Display rounding never
  replaces the stored value. No floating-point money anywhere.
- Ledger and lots: `NativePortfolioMath` (event ledger: opening balance,
  buy, sell, bonus, split, dividend, interest, valuation, repayment,
  correction as a new entry, never an overwrite) and `NativeTaxLotTrace`
  (FIFO lots, cost provenance, tax-lot tracing).
- Returns: `NativeMoneyWeightedReturn` — money-weighted return (XIRR).
  Doubles exist only inside the solver; stored cash flows and prices stay
  scaled integers. Missing or invalid flows yield N/A, never a fabricated
  return.
- Indian investment taxonomy (8 categories): equity, mutual fund, gold/SGB,
  fixed income (PPF, EPF, NPS, SSY, bank FD, SCSS, NSC, KVP, POMIS,
  post-office RD, RBI bonds, GOI bonds), real estate (incl. REIT, InvIT),
  specialised (PMS, AIF, unlisted equity, ESOP), cash/bank, liability
  (home, car, education, personal loans).
- Projections and planners: PPF/SSY passbook accrual and projection,
  FD/SGB projection, EMI planner, interest-payout planner, annual
  interest credit.
- Vault: `EncryptedVaultStore` + `KeystoreWrappedVaultKey` — SQLCipher
  database, Keystore-wrapped key, fail-closed corruption handling.
  Schema v9 catalog and migration plan in `core/database`.
- CAS import: `ParserBoundary` plus the frozen Python CAS parser
  (CAMS / CDSL / NSDL / KFintech / NPS), run on-device via Chaquopy
  Python 3.13 with pinned wheels. Missing wheels must fail the build,
  never fall back to a fabricated statement or a remote service.
  Parsed totals are reviewed against the statement's own totals before
  anything is committed (`NativeCasOutputReviewGate`).
- Prices: AMFI NAV catalog and exchange end-of-day catalog/downloader.

## REBUILD — app shell (structure, screens, navigation)

- `MainActivity` currently holds app state directly (theme, staged PDF
  and CSV bytes, books-open, device-verified, unlocked flags). It must
  be split into ViewModels and a real navigation graph.
- `NativeBooksRepository` is 1,352 lines covering people, goals,
  holdings, XIRR, tax, PPF, EMI, NAV, Gmail consent, and reports.
  It must be split by domain.
- Navigation is hand-rolled (`NativeBooksNavigation`): destinations
  Home, Holdings, Statements, Insights, Family, with route aliasing.
  The build has no Navigation component, no ViewModel library, and no
  dependency-injection library. All three are added in the rebuild.
- Design system is 2 files (`GudTheme`, `GudComponents`), not a
  component library. A full component library, typography and shape
  system, and an icon system are built in the rebuild.

## NOT SHIPPED — shells and gated features (implement properly or remove)

- Gmail: the gateway is an interface with a fail-closed default that
  throws "Gmail OAuth is not configured". Sync ledger and schedule
  files exist, but no real Gmail access ships. The old opt-in screen
  must not return until OAuth is genuinely configured.
- Billing: Play Billing 8.0.0 is a dependency, but the shipped code is
  a status notice and a catalog probe, not a working purchase flow.
- Backup/restore (important nuance): encrypted backup writing
  (`NativeSafEncryptedBackupWriter`, AES-GCM via the storage framework)
  and restore machinery (V9 restorer, restore screens, restore tests)
  exist in code, BUT the settings screen itself states that financial
  backup and cross-device restore are unavailable in this release.
  Conclusion: restore engine parts exist; a working user-facing
  backup/restore flow does not ship. The rebuild finishes it as a
  user flow and proves it with a real restore test, and does not
  claim it as done until then.

## Build facts (starting point, all to be fixed)

- Application id `in.growurdhan.redevelopment`, version `0.0.1-m00`,
  version code 1, min SDK 26, target/compile SDK 35.
- Release minification is off and there is no signing configuration.
- Dependencies include SQLCipher, Chaquopy, WorkManager, Compose
  Material 3, and Paparazzi screenshot tests.

## Rebuild order

1. Clean core as listed above, behind clear interfaces, with its
   existing tests kept green.
2. New shell: navigation, ViewModels, dependency injection, design
   system, onboarding, and home/family/holdings/statements/insights
   screens for normal users.
3. Enterprise close-out: finished backup/restore, release signing and
   minification, real versioning, Gmail-or-remove decision, Billing-
   or-remove decision, crash reporting, accessibility pass, and a
   proven release build.
