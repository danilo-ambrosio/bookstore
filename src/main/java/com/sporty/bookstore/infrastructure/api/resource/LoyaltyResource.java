package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.domain.model.loyalty.Beneficiary;
import com.sporty.bookstore.domain.model.loyalty.Loyalty;
import com.sporty.bookstore.infrastructure.api.resource.data.LoyaltyPointsData;
import com.sporty.bookstore.infrastructure.repository.LoyaltyData;
import com.sporty.bookstore.infrastructure.repository.LoyaltyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/loyalties")
public class LoyaltyResource {

    private final LoyaltyRepository loyaltyRepository;

    public LoyaltyResource(final LoyaltyRepository loyaltyRepository) {
        this.loyaltyRepository = loyaltyRepository;
    }

    @GetMapping(path="/points", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LoyaltyPointsData> queryPoints(final Beneficiary beneficiary) {
        final int totalPoints =
                loyaltyRepository.findById(beneficiary.id())
                        .map(LoyaltyData::toLoyalty)
                        .map(Loyalty::points)
                        .orElse(0);

        return ResponseEntity.ok().body(LoyaltyPointsData.of(totalPoints));
    }

}
