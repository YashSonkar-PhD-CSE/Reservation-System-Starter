# Class Activity: Design Pattern Implementation

## Team 17:
####  Aditya
####  Sanket
####  Priyank
####  Niteesh
####  Yash

### 1. Adapter Pattern:
**Implemented in**: `flight.reservation.plane`

**Reason**: There are 3 kinds of planes that the code supported earlier:
* Helicopter
* PassengerDrone
* PassengerPlane
These classes had different methods for getting the capacity of the plane. We created an interface `Plane` and used adapters to convert each of the classes to the interface. This enables us to use Plane objects in newer code while not breaking the code that directly used the classes.
#### Code samples:
```java
// Plane Interface
package flight.reservation.plane;

public interface Plane {
    public int getPassengerCapacity();
    public int getCrewCapacity();
    public String getModel();
}

// PassengerPlane class
package flight.reservation.plane;

public class PassengerPlane {

    public String model;
    public int passengerCapacity;
    public int crewCapacity;

    public PassengerPlane(String model) {
        this.model = model;
        switch (model) {
            case "A380":
                passengerCapacity = 500;
                crewCapacity = 42;
                break;
            case "A350":
                passengerCapacity = 320;
                crewCapacity = 40;
                break;
            case "Embraer 190":
                passengerCapacity = 25;
                crewCapacity = 5;
                break;
            case "Antonov AN2":
                passengerCapacity = 15;
                crewCapacity = 3;
                break;
            default:
                throw new IllegalArgumentException(String.format("Model type '%s' is not recognized", model));
        }
    }

}

// PassengerPlaneAdapter class
package flight.reservation.plane.adapters;

import flight.reservation.plane.PassengerPlane;
import flight.reservation.plane.Plane;

public class PassengerPlaneAdapter implements Plane {
    private PassengerPlane passengerPlane;

    public PassengerPlaneAdapter(PassengerPlane passengerPlane) {
        this.passengerPlane = passengerPlane;
    }

    @Override
    public int getPassengerCapacity() {
        return this.passengerPlane.passengerCapacity;
    }

    @Override
    public int getCrewCapacity() {
        return this.passengerPlane.crewCapacity;
    }

    @Override
    public String getModel() {
        return this.passengerPlane.model;
    }
    
}
```
**Note**: Some planes didn't contain fields for passenger and crew capacity. For them reasonable assumptions were taken.

