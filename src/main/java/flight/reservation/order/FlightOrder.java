package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.flight.ScheduledFlight;
import flight.reservation.payment.CreditCard;
import flight.reservation.payment.Paypal;
import flight.reservation.payment.PaymentStrategy;
import flight.reservation.payment.paymentImpls.PaypalPayment;
import flight.reservation.payment.paymentImpls.CreditCardPayment;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FlightOrder extends Order {

    private PaymentStrategy paymentStrategy;
    private final List<ScheduledFlight> flights;
    static List<String> noFlyList = Arrays.asList("Peter", "Johannes");

    public FlightOrder(List<ScheduledFlight> flights) {
        this.flights = flights;
    }

    private void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public static List<String> getNoFlyList() {
        return noFlyList;
    }

    public List<ScheduledFlight> getScheduledFlights() {
        return flights;
    }

    private boolean isOrderValid(Customer customer, List<String> passengerNames, List<ScheduledFlight> flights) {
        boolean valid = true;
        valid = valid && !noFlyList.contains(customer.getName());
        valid = valid && passengerNames.stream().noneMatch(passenger -> noFlyList.contains(passenger));
        valid = valid && flights.stream().allMatch(scheduledFlight -> {
            try {
                return scheduledFlight.getAvailableCapacity() >= passengerNames.size();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return false;
            }
        });
        return valid;
    }

    public boolean processOrderWithCreditCardDetail(String number, Date expirationDate, String cvv) throws IllegalStateException {
        CreditCardPayment creditCardPayment = new CreditCardPayment(new CreditCard(number, expirationDate, cvv));
        return processOrder(creditCardPayment);
    }

    public boolean processOrderWithPayPal(String email, String password) throws IllegalStateException {
        PaypalPayment paypalPayment = new PaypalPayment(email, password);
        return processOrder(paypalPayment);
    }

    private boolean processOrder(PaymentStrategy paymentStrategy) throws IllegalStateException {
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
