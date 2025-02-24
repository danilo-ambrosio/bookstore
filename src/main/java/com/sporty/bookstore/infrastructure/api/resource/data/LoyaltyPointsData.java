package com.sporty.bookstore.infrastructure.api.resource.data;

public class LoyaltyPointsData {

    public final int total;

    public static LoyaltyPointsData of(int total) {
        return new LoyaltyPointsData(total);
    }

    private LoyaltyPointsData(int total) {
        this.total = total;
    }

}
