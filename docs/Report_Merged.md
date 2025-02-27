# Class Activity: Design Pattern Implementation

## Team 17:
* Aditya Singh Rathore (2024204012)
* Sanket Adlak (2024204005)
* Priyank Nagarnaik (2024204011)
* Niteesh Chandra (2024801002)
* Yash Sonkar (2024801001)

### Design Patterns Implemented:
* Adapter Pattern
* Singleton Pattern
* Strategy Pattern

## Adapter Pattern:
**Introduction**: The Adapter Pattern is a structural design pattern that acts as a bridge between two incompatible interfaces. 
It allows previously incompatible classes to work together by converting the interface 
of one class into an interface expected by the clients.

**Requirement**: The code contains multiple types of flights namely:
* PassengerPlane
* PassengerDrone
* Helicopter

Originally, each of these was implemented as a separate class.

**Benefits of using Adapter pattern**: Using `Adapter` pattern, we can now use an interface `Plane` instead of the separate classes.

Snippet from implementation:
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

// Using the adapter class (sample from RUnner.java)
static List<Plane> aircrafts = Arrays.asList(
            new PassengerPlaneAdapter(new PassengerPlane("A380")),
            new PassengerPlaneAdapter(new PassengerPlane("A350")),
    );
```
**Note**: Some planes didn't contain fields for passenger and crew capacity. For them reasonable assumptions were taken.

## Singleton Pattern
**Introduction**: The Singleton Pattern ensures that a class has only one instance and provides a global point of access to it. 

**Requirement**: In the original implementation, it was possible for multiple instances of schedule to exist during the application lifecycle. This can cause issues since both schedules would then have different data which we don't want. 

**Benefits of using Singleton Pattern**: Using Singleton pattern we ensure that only one instance of `Schedule` class exists during the application lifecycle. This ensures that the data is consistent when it is used from `Schedule`.

Snippet from implementation:
Original Class:
```java
package flight.reservation.flight;

public class Schedule {

    // Class attributes (only scheduledFlights for now)

    public Schedule() {
        // Constructor
    }
    
    // Other methods
}
```

Modified class (using Singleton pattern)
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
            instance = new Schedule();
        }
        return instance;
    }

    // Other methods
}
```

## Strategy Pattern
**Introduction**: The Strategy Pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. 

**Requirement**: 

**Benefits of using Strategy pattern**: The Strategy pattern is employed to provide a flexible and extensible way to manage different payment methods. It allows the system to support new payment methods without modifying the core payment processing logic. Each payment method is encapsulated in a separate strategy class, implementing a common interface. This promotes loose coupling and adheres to the Open/Closed Principle, making the system more maintainable and adaptable to future payment options.

Snippet from implementation
PaymentStrategy Interface:
```java
package flight.reservation.payment;

public interace PaymentStrategy {
    boolean pay(double amount);
}
```

Paypal Payment Strategy (implements PaymentStrategy Interface)
```java
package flight.reservation.payment.paymentImpls;

import flight.reservation.payment.PaymentStrategy;
import flight.reservation.payment.Paypal;

public class PaypalPayment implements PaymentStrategy {
    private String email;
    private String password;

    public PaypalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean pay(double amount) {
        if (email.equals(Paypal.DATA_BASE.get(password))) {
            System.out.println("Paying " + amount + " using PayPal.");
            return true;
        }
        return false
    }
}
```

CreditCardPayment was implemented similarly.

Changes to FlightOrder class (since this class is responsible for calling payments)
```java
package flight.reservation.order;

// Imports

public class FlightOrder extends Order {

    private PaymentStrategy paymentStrategy; // Any concrete class implementing PaymentStrategy interface can be used
    private final List<ScheduledFlight> flights;
    static List<String> noFlyList = Arrays.asList("Peter", "Johannes");

    public FlightOrder(List<ScheduledFlight> flights) {
        this.flights = flights;
    }

    private void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Other methods

    public boolean processOrderWithCreditCardDetail(String number, Date expirationDate, String cvv) throws IllegalStateException {
        // Handles creation of payment strategy for credit card payments.
        CreditCardPayment creditCardPayment = new CreditCardPayment(number, expirationDate, cvv);
        return processOrder(creditCardPayment);
    }

    public boolean processOrderWithPayPal(String email, String password) throws IllegalStateException {
        // Handles creation of payment strategy for paypal payments
        PaypalPayment paypalPayment = new PaypalPayment(email, password);
        return processOrder(paypalPayment);
    }

    private boolean processOrder(PaymentStrategy paymentStrategy) throws IllegalStateException {
        // Payments using any method will be handled using this method.
        if(isClosed()) {
            return true; // Payment already processed
        }
        this.setPaymentStrategy(paymentStrategy);

        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment Strategy not set");
        }

        boolean isPaid = paymentStrategy.pay(this.getPrice());
        if(isPaid) {
            this.setClosed();
        }
        return isPaid;
    }
}
```