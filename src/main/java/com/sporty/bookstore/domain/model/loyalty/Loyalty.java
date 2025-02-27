package com.sporty.bookstore.domain.model.loyalty;

/**
 * This class represents the loyalty program for a beneficiary.
 * It keeps track of the total points accumulated by the beneficiary
 * and provides methods to use and accumulate points based on the
 * number of books purchased.
 *
 * @author Danilo Ambrosio
 */
public class Loyalty {

    private final Beneficiary beneficiary;
    private Points points;

    public static Loyalty of(final Beneficiary beneficiary) {
        return accumulated(beneficiary, Points.of(0));
    }

    public static Loyalty accumulated(final Beneficiary beneficiary,
                                      final Points points) {
        return new Loyalty(beneficiary, points);
    }

    private Loyalty(final Beneficiary beneficiary,
                   final Points points) {
        this.beneficiary = beneficiary;
        this.points = points;
    }

    public void useFor(final int bookQuantity) {
        if (!cover(bookQuantity)) {
            throw new InsufficientLoyaltyPointsException();
        }
        points = this.points.use(Points.requiredFor(bookQuantity));
    }

    public void accumulate(int bookQuantity) {
        points = this.points.accumulateFrom(bookQuantity);
    }

    public boolean cover(final int bookQuantity) {
        return Points.requiredFor(bookQuantity).total <= points.total;
    }

    public String beneficiaryId() {
        return beneficiary.id();
    }

    public int points() {
        return points.total;
    }

}
