# NBC Android Apps v1.6.1 - API v9.12.0

Two separate APKs are produced from this project:

- `passengerDebug` — passenger dashboard, trip search, authoritative fare quote, segment-aware seats, bookings, tickets, payments, Bus Pass purchase/QR, and station boards.
- `operationsDebug` — capability-gated assigned runs, run detail/manifest, ticket validation/check-in/boarding, Bus Pass validation, authorized station sales, and live-run controls.

The app uses API v9.12.0 and posts Odoo JSON-RPC requests to `https://nationalbusbelize.com` with the session cookie returned by `/web/session/authenticate`. Users enter only email/username and password; the Odoo database is resolved internally.

## v1.6.1 connectivity fix

- Sign-in uses the configured NBC Odoo database directly and no longer depends on the disabled `/web/database/list` route.
- Passenger trip search sends the API-required `YYYY-MM-DD` service date.
- Search prevents identical boarding and destination stops and validates date formatting before calling the server.

## v1.6.0 API integration

- Role and capability discovery through `/api/nbc/v1/mobile/capabilities`.
- Passenger dashboard through `/api/nbc/v1/passenger/dashboard`.
- Ownership-scoped tickets, bookings, and payments.
- Server-authoritative fare quote and segment-aware seat refresh.
- Bus Pass products, purchase, list, QR presentation, and Operations validation.
- Operations run detail and manifest through `/api/nbc/v1/ops/run`.
- Station sales appear only when `station_sales` is granted by the server.
- The Passenger app never calls the staff-only station sale endpoint.
- Payment and pass states are always treated as server-authoritative.

## Passenger accounts

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
