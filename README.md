# cinema-backend

# How to run:

Prerequisites

Java 21

Gradle

Docker (optional)

1) Clone the repository
2) Navigate to the project directory: 
 cd cinema-backend
3) Run:
   ./gradlew bootRun

# Implementation
I have 2 tables in the database:
    Client and Movie.
In client there are clients stored
and in movie all the movies stored.

I am using liquibase which changelog you
can find under the resources and db.changelog
package.

I have implemented filtering too here although
i do not use it in the front-end. But you
can at least see how i would have implemented it.

CorsConfig allows for queries from the front-end
to come through. I also made exception for handling
certain cases where input parameters are invalid.

Movie has list of seats where there are 88 booleans - 
false representing that this seat has not been taken,
true representing that his seat has been taken.
Movie also has list of participants.

I also put app to Docker container and wrote tests for service layer.
# Challenges and time spent

There really was not anything challenging.

I spent in total around 7 hours on the backend.
