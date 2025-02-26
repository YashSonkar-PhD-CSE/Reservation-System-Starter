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