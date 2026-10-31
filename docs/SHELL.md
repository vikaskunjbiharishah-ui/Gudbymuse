# App Shell — navigation, design system, onboarding

Branch: `shell/navigation-design-system`
Status: **written, not yet build-verified.** There is no Java or Android
SDK on the builder machine used for this commit, so no local build was
run. The CI workflow in this branch is the verifier. A queued or running
CI job is not a pass. Do not claim this shell builds until CI reports
success on this exact branch.

## What landed

- Real Navigation component graph (`GudDestinations`): onboarding, then
  a 5-item bottom bar — Home, Holdings, Statements, Insights, Family.
  No hand-rolled navigation state. The system back button and saved
  state belong to the Navigation component.
- One theme (`GudTheme`) and one component set (`GudComponents`):
  primary and outlined buttons at least 48dp tall, section titles,
  cards, and a money display that only shows text the domain layer
  already formatted. Brand colours carried over from the earlier
  project: navy, gold, teal.
- Home screen with a ViewModel (`HomeViewModel`) and three honest
  states: loading, ready, and unavailable with a retry button. The
  home screen depends only on a `WealthSummaryRepository` interface.
- Onboarding in plain language, including the honest backup note
  below. No feature is promised before it is built.
- Gradle project: application id `in.growurdhan.app`, min SDK 26,
  target/compile SDK 35, version `0.1.0-shell` (not a release),
  minification off, no signing configuration yet. No Gradle wrapper
  jar is committed, because snapshot pushes cannot carry binaries;
  CI uses `setup-gradle` with Gradle 8.11.1 instead.

## What is NOT wired yet

- Domain core, encrypted vault, CAS parser, prices: not ported in
  this branch. Home currently wires an unconnected repository that
  fails closed with "Wealth data is not connected yet." No wealth
  numbers are shown until the real repository lands.
- Holdings, Statements, Insights, and Family screens: honest
  "being built" placeholders, not fake data or fake buttons.
- Gmail, Billing, backup/restore, signing, crash reporting: not in
  this branch. Gmail and Billing stay blocked until their services
  are genuinely configured. They are never faked from tests.

## A decision that differs from the old experiment

The old experiment deferred financial backup for its first release.
That deferral is NOT carried into this rebuild. The enterprise
decision here is to finish backup and restore as a working user
flow, because permanent data loss on losing a phone is not
acceptable for a normal user's wealth app. Until backup lands,
onboarding says so plainly instead of hiding it.

## Next steps, in order

1. Wait for CI on this branch and fix any build failure there.
2. Port the cleaned domain core behind `WealthSummaryRepository`
   and the other domain interfaces.
3. Replace the placeholder screens one by one with real screens.
4. Merge to main only after CI is green on the branch.
