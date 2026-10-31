# GrowUrDhan — Enterprise Rebuild

A local-first Indian family wealth tracker, rebuilt for normal users.

Everything stays on the phone. There is no server, no cloud account, and no
sale of financial data. The app imports Indian CAS statements
(CAMS / CDSL / NSDL / KFintech / NPS), tracks mutual funds, demat holdings,
PPF, SSY, FD, RD, SGB, NPS, property, gold, loans and EMIs, and goals —
for the whole family, in Hindi and English.

## Status

Rebuild in progress. This repository starts from the proven Kotlin domain
core of the earlier project (money calculations, CAS parsing, encrypted
vault) and rebuilds the app around it with a clean architecture, simple
navigation, and enterprise-grade hardening.

## Architecture (rebuild target)

- Kotlin / Jetpack, single Android app
- Clean layers: UI (Compose, Material 3) → ViewModel → domain
  (money, ledger, tax, XIRR, FIFO) → encrypted local storage (SQLCipher)
- Navigation for normal users: Home (total family wealth) → Family →
  Investments → Goals → Import → Backup
- No feature ships as a dead button. A feature is either implemented
  properly or it is not shown.

## Security promises

- No committed keystores, passwords, or OAuth client secrets. Ever.
  Debug and release keys are local-only and gitignored.
- Money is stored as scaled integers, never floating point.
- Financial data is append-only in the ledger; corrections are new entries.
- Encrypted backup and restore, so losing a phone does not mean losing data.

## What is not here yet

Gmail sync, Play Billing, and the release signing setup are being rebuilt
properly in this repository. Nothing in this README promises them until
they land with tests.
