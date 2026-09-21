# NBC Android Apps v1.4 Reference Design

Two separate APKs are produced from this project:

- `passengerDebug` — trip search, stop loading, ticket status, authenticated booking shell, and public board.
- `operationsDebug` — secure Odoo session sign-in, assigned runs, manifest, ticket validation/check-in/boarding, and live-run start.

The app uses the API guide v9.9.0 and posts Odoo JSON-RPC requests to `https://nationalbusbelize.com` with the session cookie returned by `/web/session/authenticate`. It does not open the website in a browser and does not collect card PAN/CVC data.

The v1.4 interface follows the NBC product rendering: a Traveler dashboard with From/To/date search, upcoming trip cards, boarding-pass presentation, QR/ticket status, and a focused bottom navigation; Operations includes an assigned-run hero card, quick actions, live tracking, and run progress. Login accepts only email/username and password; the Odoo database is resolved internally.

## Build

Open the `NBC_Android_Apps` folder in Android Studio, or run:

```bash
./gradlew assemblePassengerDebug assembleOperationsDebug
```

APK output:

```text
app/build/outputs/apk/apk/passenger/debug/app-passenger-debug.apk
app/build/outputs/apk/apk/operations/debug/app-operations-debug.apk
```

Before production release, set the real Odoo database name in the sign-in screen and configure a release signing key. The backend must grant the signed-in user the corresponding NBC roles.
