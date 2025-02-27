package flight.reservation.payment.paymentImpls;

import flight.reservation.payment.PaymentStrategy;
import flight.reservation.payment.CreditCard;

public class CreditCardPayment implements PaymentStrategy {
    private CreditCard card ;

    public CreditCardPayment(CreditCard card) {
        this.card = card;
    }

    @Override
    public boolean pay(double amount) {
        iif (card.isValid() && card.getAmount() >= amount) {
            System.out.println("Paying " + amount + " using Credit Card.");
            card.setAmount(card.getAmount() - amount);
            return true;
        }
        return false;
    }
}