# StayGo (Checkpoint2)

Android hotel booking mock app in Kotlin inspired by hotel search flows.

## Features

- Hotel search and listing with:
  - Destination query input
  - Price range filter
  - Rating filter
  - Card-based `RecyclerView`
- Hotel details screen with:
  - Banner image
  - Hotel name, location, rating
  - Price section
  - Amenities and description
  - Fixed **Book Now** button
- Mock booking system with:
  - Check-in/check-out date pickers
  - Guest count input
  - Auto total calculation (`pricePerNight * nights * guests`)
  - Confirmation dialog
  - Booking persistence via `SharedPreferences`
- Mock login/logout with local session persistence

## Mock Data

- Source: `app/src/main/assets/hotels.json`
- Includes 10 hotels with fields:
  - `id`, `name`, `location`, `pricePerNight`, `rating`, `image`, `description`, `amenities`

## Project Structure

- `app/src/main/java/com/example/checkpoint2/`
  - `MainActivity.kt` (search + listing)
  - `HotelDetailsActivity.kt` (hotel details)
  - `BookingActivity.kt` (mock booking)
  - `LoginActivity.kt` (mock auth)
- `app/src/main/java/com/example/checkpoint2/data/`
  - `HotelRepository.kt`, `BookingRepository.kt`, `SessionManager.kt`
- `app/src/main/java/com/example/checkpoint2/model/`
  - `Hotel.kt`, `Booking.kt`
- `app/src/main/res/layout/`
  - `activity_main.xml`, `item_hotel_card.xml`, `activity_hotel_details.xml`, `activity_booking.xml`, `activity_login.xml`

## Run

1. Open project in Android Studio.
2. Sync Gradle.
3. Run app on emulator or device.

## Tests

- Unit tests are in `app/src/test/java/com/example/checkpoint2/ExampleUnitTest.kt`.
- These cover booking night calculation and total price formula.

## Note

CLI Gradle test execution needs Java configured (`JAVA_HOME` or `java` in `PATH`).

