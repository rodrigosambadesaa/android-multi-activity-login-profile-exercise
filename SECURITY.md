# Java4Shell — CVE-2026-114938

This Android project is built by OpenJDK-based Java tooling in GitHub Actions,
while the installed Android application runs on ART rather than OpenJDK HotSpot.

The Android CI workflow sets `JAVA_TOOL_OPTIONS=-XX:-UseBASE64Intrinsics`
as an interim mitigation to disable the vulnerable Base64 hardware intrinsic
in build JVMs. It does not change app runtime behavior.

Local builds and self-hosted runners must install a **vendor-confirmed
patched JDK**, or apply this flag temporarily and restart all Java/Gradle
processes. Check `java -XX:+PrintFlagsFinal -version` for
`UseBASE64Intrinsics = false`.
PowerShell: `$env:JAVA_TOOL_OPTIONS = '-XX:-UseBASE64Intrinsics'`.

See https://www.cert.europa.eu/publications/security-advisories/2026-021/
and https://adoptium.net/news/2026/11/temurin-november-2026-cspu-java4shell/.
