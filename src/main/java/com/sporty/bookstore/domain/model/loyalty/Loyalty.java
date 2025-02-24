package com.sporty.bookstore.domain.model.loyalty;

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

    public String beneficiaryId() {
        return beneficiary.id();
    }

    public int points() {
        return points.total;
    }

    public boolean cover(final int bookQuantity) {
        return Points.requiredFor(bookQuantity).total <= points.total;
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

}
