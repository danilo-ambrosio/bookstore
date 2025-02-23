package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.domain.model.purchase.PaymentDetail;
import com.sporty.bookstore.domain.model.purchase.Purchase;
import com.sporty.bookstore.domain.model.purchase.Customer;
import com.sporty.bookstore.infrastructure.api.resource.data.PurchaseData;
import com.sporty.bookstore.infrastructure.api.resource.data.PurchaseItemData;
import com.sporty.bookstore.usecase.purchase.PurchaseUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/purchases")
public class PurchaseResource {

    private final PurchaseUseCase purchaseUseCase;

    public PurchaseResource(final PurchaseUseCase purchaseUseCase) {
        this.purchaseUseCase = purchaseUseCase;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PurchaseData> price(@RequestBody final PurchaseData purchaseData, final Customer customer) {
        final List<PaymentDetail> paymentDetails = purchaseData.items.stream().map(PurchaseItemData::toDetail).toList();
        final Purchase purchase = purchaseUseCase.process(customer, paymentDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(PurchaseData.from(purchase));
    }

}
