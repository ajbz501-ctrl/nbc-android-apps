# NBC Android Apps v1.5.0 - API v9.9.1

Two separate APKs are produced from this project:

- `passengerDebug` — trip search, stop loading, ticket status, authenticated booking shell, and public board.
- `operationsDebug` — secure Odoo session sign-in, assigned runs, manifest, ticket validation/check-in/boarding, and live-run start.

The app uses the API guide v9.9.1 and posts Odoo JSON-RPC requests to `https://nationalbusbelize.com` with the session cookie returned by `/web/session/authenticate`. It does not open the website in a browser and does not collect card PAN/CVC data.

## v1.5.0 passenger accounts

- Public Passenger signup through `/api/nbc/v1/account/signup`.
- Passenger profile, server-generated NBC account number, and real account QR.
- Passenger name/phone updates through `/api/nbc/v1/account/update`.
- Real ticket QR rendering from the ticket QR token.
- Staff passenger lookup by account number or account QR.
- Passenger account QR remains separate from ticket/boarding QR.

The interface follows the NBC product rendering: a Traveler dashboard with From/To/date search, upcoming trip cards, boarding-pass presentation, QR/ticket status, and a focused bottom navigation; Operations includes an assigned-run hero card, quick actions, live tracking, and run progress. Login accepts only email/username and password; the Odoo database is resolved internally.

## v1.4.2 navigation update

- Android and in-app back navigation from secondary screens.
- Passenger tabs: Home, My Trips, Boards, Account.
- Public arrival/departure boards through `/api/nbc/status_board`.
- Operations live-run controls for start, active runs, and end tracking.
- Booking search stays on Home; purchased journeys and tickets are under My Trips.

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

Before production release, configure a release signing key. The backend must grant Operations users the corresponding NBC roles.
