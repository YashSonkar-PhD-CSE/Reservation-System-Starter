# Pattern Implemented: Singleton
Singleton pattern was implemented for Schedule class. 
This was done to ensure that there is only one instance of Schedule throughout the application.

Original Class:
```java
package flight.reservation.flight;

public class Schedule {

    // Class attributes (only scheduledFlights for now)

    public Schedule() {
        // Constructor
    }
    
    // Other methods
```

Modified class (using SIngleton pattern)
```java
package flight.reservation.flight;

public class Schedule {

    private static Schedule instance;
    // Other Class Attributes

    private Schedule() {
        // Constructor but private to prevent creation of multiple objects
    }

    public static Schedule getInstance() {
        // This ensures that only one instance of the class
        if (instance == null) {
            instance = new Schedule;
        }
        return instance;
    }

    // Other methods
```