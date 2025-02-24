package com.sporty.bookstore.infrastructure.repository;

import com.sporty.bookstore.domain.model.loyalty.Beneficiary;
import com.sporty.bookstore.domain.model.loyalty.Loyalty;
import com.sporty.bookstore.domain.model.loyalty.Points;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loyalty")
public class LoyaltyData {

    @Id
    public final String beneficiaryId;
    public final int points;

    @PersistenceCreator
    public LoyaltyData(final String beneficiaryId,
                       final int points) {
        this.beneficiaryId = beneficiaryId;
        this.points = points;
    }

    public static LoyaltyData from(final Loyalty loyalty) {
        return new LoyaltyData(loyalty.beneficiaryId(), loyalty.points());
    }

    public Loyalty toLoyalty() {
        return Loyalty.accumulated(Beneficiary.identifiedBy(beneficiaryId), Points.of(points));
    }
}
