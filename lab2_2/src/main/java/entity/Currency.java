package entity;

/**
 * @author friendlyevil
 */
public enum Currency {
    USD(1), RUB(73.2), EUR(0.8);

    private final double coef;

    Currency(double coef) {
        this.coef = coef;
    }

    public double convert(double price, Currency to) {
        return price * to.coef / this.coef;
    }
}
