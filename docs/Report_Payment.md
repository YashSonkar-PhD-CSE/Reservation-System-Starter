# Pattern Implemented: Strategy
Strategy pattern was implemented for Payment
This was done to make it easier to handle CreditCard and Paypal Payments. Also, this pattern ensures that new methods of payments are easier to add.

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