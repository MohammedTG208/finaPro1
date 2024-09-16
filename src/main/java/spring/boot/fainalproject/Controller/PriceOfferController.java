package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.PriceOfferService;

@RestController
@RequestMapping("/api/v1/price-offer")
@RequiredArgsConstructor
public class PriceOfferController {
    private final PriceOfferService priceOfferService;

    @GetMapping
    public ResponseEntity getAllPriceOffers() {
        return ResponseEntity.status(200).body(priceOfferService.getAllPriceOffers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getPriceOfferById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(priceOfferService.getPriceOfferById(id));
    }

    @PostMapping("/create")
    public ResponseEntity createPriceOffer(@AuthenticationPrincipal User user,
                                           @RequestParam Integer recycleId,

                                           @Valid @RequestBody PriceOffer priceOffer) {
        priceOfferService.createPriceOffer(recycleId, user.getId(), priceOffer);
        return ResponseEntity.status(200).body("PriceOffer created successfully");
    }

    @PutMapping("/update/{priceOfferId}")
    public ResponseEntity updatePriceOffer(@AuthenticationPrincipal User user,
                                           @PathVariable Integer priceOfferId,
                                           @Valid @RequestBody PriceOffer updatedPriceOffer) {
        priceOfferService.updatePriceOffer(priceOfferId, user.getId(), updatedPriceOffer);
        return ResponseEntity.status(200).body("PriceOffer updated successfully");
    }

    @DeleteMapping("/delete/{priceOfferId}")
    public ResponseEntity deletePriceOffer(@AuthenticationPrincipal User user,
                                           @PathVariable Integer priceOfferId
    ) {
        priceOfferService.cancelPriceOffer(priceOfferId, user.getId());
        return ResponseEntity.status(200).body("PriceOffer deleted successfully");
    }
}
