# NBC Android Apps v1.8.1 - API v9.12.2

## v1.8.1 operations improvements

- Scanner actions show a clean ticket result card instead of raw JSON.
- Onboard sales require pickup and drop-off selections and submit their stop IDs.
- Sale confirmation retains the passenger's pickup and drop-off points.
- Form fields are taller, larger, and bold for easier handheld use.

Two separate APKs are produced from this project:

- `passengerDebug` — passenger dashboard, trip search, authoritative fare quote, segment-aware seats, bookings, tickets, payments, Bus Pass purchase/QR, and station boards.
- `operationsDebug` — capability-gated assigned runs, run detail/manifest, ticket validation/check-in/boarding, Bus Pass validation, authorized station sales, and live-run controls.

The app uses API v9.12.2 and posts Odoo JSON-RPC requests to `https://nationalbusbelize.com` with the session cookie returned by `/web/session/authenticate`. Users enter only email/username and password; the Odoo database is resolved internally.

## v1.7.2 passenger seat booking security

- Uses the v9.12.2 ownership-scoped passenger booking contract.
- Passengers can view the seat map and reserve a selected seat without an NBC operational role.
- The selected seat is refreshed and rechecked immediately before booking creation.
- Passengers can book only for their own commercial partner; station staff permissions remain separate.

## v1.7.3 Operations phone access

- Native camera QR scanner with Android runtime camera permission.
- Manual ticket-code entry remains available when camera access is denied.
- Precise-location runtime permission and phone GPS availability checks.
- Live coordinates are posted to `/api/nbc/live/gps/update` while tracking is active.
- Tracking sends latitude, longitude, accuracy, speed, heading and timestamp.

## v1.7.4 embedded scanner and conductor sales

- Replaces the separate camera activity with an embedded in-app QR scanner.
- Camera pauses safely when leaving the scanner or pressing Back.
- Conductors can sell onboard tickets through `/api/nbc/conductor/sell_ticket`.
- Onboard sales support adult, child, senior and student fares.
- Cash, card/POS and approved customer-account collection are available.
- The server calculates fare, assigns/validates the seat and records the selling conductor.

## v1.7.5 live tracking assigned run and 80mm printing

- Live GPS display follows the Operations phone position.
- Onboard ticket sales automatically load the conductor's assigned run.
- Manual Trip ID entry is available only when no assignment is returned.
- Paired Bluetooth ESC/POS printer selection with Android runtime permission.
- Professional 80mm ticket output with passenger, run, route, seat, fare, payment and QR.

## v1.7.6 scanner targets and run codes

- Scanner menu now supports ticket QR, passenger-account QR, and Bus Pass QR.
- Conductor sale accepts run codes such as `NBC-BZE-1815` and resolves them to the server trip ID.
- Assigned-run loading displays the conductor's run code automatically.
- GPS screen always shows a visible map panel and coordinate fallback while tiles load.

## v1.7.7 map and scanner audit

- Replaced blocked external OpenStreetMap tiles with an offline-safe NBC live map that always renders the GPS marker, accuracy radius, Belize route context, and live run label.
- GPS updates move the existing marker in place instead of reloading the map on every location update.
- Consolidated ticket QR, passenger-account QR, and Bus Pass scanning into one Operations Scanner hub.
- Removed duplicate standalone Scanner / Validate Bus Pass / Passenger Account navigation entries; Bus Pass validation remains server-authoritative through the same scanner flow.

## v1.7.8 street map and assigned run codes

- Replaced the simplified Belize diagram with a recognizable interactive street map using MapLibre and OpenFreeMap.
- Added an in-app GPS fallback that clearly shows coordinates if online map data cannot load.
- Loads the logged-in conductor or driver's assigned run automatically from `/api/nbc/v1/ops/my_runs`.
- Displays and accepts the API run number, such as `NBC-BZE-1815`, while preserving the internal numeric `trip_id`.
- Resolves and verifies the run through `/api/nbc/v1/ops/run` before tracking starts.
- Corrected live telemetry parameter names to `speed_kmh` and `accuracy_m` to match the NBC API.

## v1.8.0 full Bluetooth ticket printing

- Prints ticket number, passenger, run, route, departure, seat, fare, payment, status, and issue time.
- Uses a raster QR image for broad ESC/POS printer compatibility.
- Prevents duplicate prints while a Bluetooth job is active.

## v1.7.9 onboard ticket-sale fix

- Reuses the numeric `trip_id` returned with the conductor's assigned run instead of discarding it.
- Resolves manually entered run numbers through `/api/nbc/v1/ops/run` rather than the legacy trips endpoint.
- Keeps the readable run code, including `NBC-BZE-1815`, visible on the sale form and printed ticket.

## v1.7.1 booking completion and seats

- Segment-aware visual seat selection using the live seat map.
- Available, unavailable, and selected seat states.
- Required seat selection and immediate server recheck before booking.
- Booking confirmation continues directly to Pay Now or View Bookings.
- Shows all online and station payment options, including unavailable provider status.
- Cash, station card, and NBC account/credit remain staff-confirmed station payments.

## v1.7.0 passenger payments

- Unified Passenger Pay Now for owned bookings and Bus Passes.
- Live payment-method discovery from NBC.
- DigiWallet TAC/OTP initiation and confirmation.
- E-Kyash and Belize Bank provider initiation with secure in-app hosted payment view.
- Server-authoritative payment status refresh and ticket access after confirmation.
- Correct booking totals and balances without `NaN` display values.
- Cash, station card, and account/credit remain capability-gated Operations flows.

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
