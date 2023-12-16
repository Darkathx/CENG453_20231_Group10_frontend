# Group10 - Catan - Frontend Development

This module consists of the implentations for client-side development of desktop application of our Catan game.

- Detailed documentation of the project can be found on ***.


## Used Libraries
- Spring Web
- JavaFX

## Getting Started

### Prerequisites

- JDK 21 or later
- Gradle (as defined in `gradle/wrapper/gradle-wrapper.properties`)

### Build and run:

The application uses Spring profiles for different environments, with configurations located under `src/main/resources/application.properties`.

#### Local Development

To start the application in the local environment, run the following command:

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

#### Production Environment 

To build the application for production, run:

```bash
./gradlew build
```

Then, to execute the application, use:
```bash
java -jar -Dspring.profiles.active=prod build/libs/catan-frontend-group10-0.0.1-SNAPSHOT.jar
```

## Project Structure

  - `src/main/java/edu/odtu/ceng453/group10/catanfrontend`: Main application package.
  - `/config`: Configuration classes (e.g., Settings, LeaderboardType).
  - `/game`: Consists of game mechanics related objects, such as Player, GameState, Tile...
  - `/requests`: Request object for sending requests to backend side of the project.
  - `/ui`: UI components of the project like main screen or leaderboard screens.
 
 ## API Documentation

  Visit [API Documentation - Swagger UI](https://catan-backend-ds1e.onrender.com/swagger-ui/index.html) for the interactive Swagger UI documentation of the API.

## Database Schema

Database schema did not change in frontend phase, as we handled in-game issues in the client. Frontend sends requests to backend when needed, such as login, register, leaderboard functionalities.

## Testing 


## Authors 
- Meric Kerem Yalçın
- Mustafa Bera Türk
