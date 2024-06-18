# producer-consumer

a simple message-driven application in core Java that simulates a producer-consumer scenario using a message queue.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Gradle build tool

## Project Structure
```

project-root/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── learning/
│   │               └── assignment/
│   │                   ├── Consumer.java
│   │                   ├── Message.java
│   │                   ├── MessageQueue.java
│   │                   ├── Producer.java
│   │                   ├── util/
│   │                       └── ThreadUtil.java
│   └── test/
│       └── java/
│           └── com/
│               └── learning/
│                   └── assignment/
│                       └── test/
│                           └── ProducerConsumerTest.java
│
├── build.gradle
└── README.md

```

## Running the Tests

To run the test :

1. Open a terminal.
2. Navigate to the project directory.
3. Run the following command:

    ```
    ./gradlew clean test
    ```
## Example Output
```
    ProducerConsumerTest > testFailureScenario() PASSED
    ProducerConsumerTest > testSuccessfulProcessing() PASSED
```
## Additional Notes

- The `Producer` and `Consumer` classes use a shared `MessageQueue` to demonstrate thread synchronization.
- The `Message` class encapsulates a message with an ID and data.
- The `ThreadUtil` class provides utility methods for thread operations.
- The tests ensure that producers and consumers behave correctly under varying conditions.
- Expected output includes messages indicating successful production and consumption counts.
- Error handling scenarios are tested to ensure the robustness of the application.


## Conclusion

This project showcases the producer-consumer pattern in Java, demonstrating thread synchronization and error-handling techniques. Thanks for checking out this project!



