# Decisions

Plain-language record of the choices made for this rebuild. Technical
choices are made by the builder; this log exists so anyone can see why.

## 2026-10-31 — Continue from Kotlin, rebuild the shell

- Decision: start from the existing Kotlin/Jetpack work, not from zero
  and not from the Tauri build.
- Why: the money engine and the encrypted vault in the Kotlin work are
  the hardest parts to get right, and they are already proven. Throwing
  them away would risk wrong numbers for real people's money.
- What changes completely: navigation, screens, design system, and app
  structure. What is kept and protected: money math, CAS parsing, ledger,
  tax rules, and the encrypted vault.

## 2026-10-31 — No copied secrets, no dead features

- The old debug keystore and its published password and OAuth client
  are NOT copied into this repository, and must never be reused for
  production.
- No button or setting ships unless the feature behind it actually
  works. The old Gmail opt-in that failed, and the Billing shell, are
  out until they are implemented properly.

## 2026-10-31 — Built for normal Indian users

- Navigation and wording come first: a person who has never used a
  finance app should understand the home screen without help.
- Hindi and English. Indian investment types first: mutual funds,
  demat, PPF, SSY, FD, RD, NPS, SGB, gold, property, loans, goals.
