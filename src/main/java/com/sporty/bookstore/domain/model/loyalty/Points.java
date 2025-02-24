package com.sporty.bookstore.domain.model.loyalty;

public class Points {

    final int total;

    public static Points of(final int total) {
        return new Points(total);
    }

    public static Points requiredFor(int books) {
        return Points.of(books * 10);
    }

    private Points(final int total) {
        this.total = total;
    }

    public Points use(Points points) {
        return Points.of(this.total - points.total);
    }

    public Points accumulateFrom(int bookQuantity) {
        return new Points(this.total + bookQuantity);
    }
}
