# Android Multi-Activity Login & Profile Exercise

Modernized Java solution of the DAM exercise **Programación con varias activities y AsyncTask**.

The original assignment asks for a three-Activity flow with a serializable `Usuario`, a simulated login (`neo` / `sesamo`), registration/profile editing, results passed between Activities, a menu and an `AsyncTask`-based login simulation. This version preserves that academic behavior while replacing obsolete Android APIs with current equivalents.

## Modernization

- Java 17.
- AndroidX / AppCompat.
- View Binding.
- Activity Result API instead of `startActivityForResult` / `onActivityResult`.
- `Parcelable` user model instead of Java `Serializable`.
- `ExecutorService` instead of deprecated `AsyncTask`.
- Lifecycle-safe cancellation/cleanup.
- Input validation extracted into testable code.
- Unit tests and GitHub Actions.

## Demo credentials

- Nick: `neo`
- Password: `sesamo`

A successful login creates the same demonstration user required by the original exercise and opens the orders screen. Registration and profile editing return a `UserProfile` through the Activity Result API.

## Build

This project targets the current Android toolchain used for this modernization batch:

- Android Gradle Plugin 9.3.0
- Gradle 9.5.0
- JDK 17
- `compileSdk` / `targetSdk` 37
- `minSdk` 23

If you want a local Gradle wrapper, run:

```bash
gradle wrapper --gradle-version 9.5.0
```

Then:

```bash
./gradlew testDebugUnitTest lintDebug assembleDebug
```

The GitHub Actions workflow installs Gradle 9.5 directly, so the repository does not need to commit wrapper binaries.

## Historical review

See [`docs/ORIGINAL_REVIEW.md`](docs/ORIGINAL_REVIEW.md) for the assessment of the 2019 implementation and the changes made here.
