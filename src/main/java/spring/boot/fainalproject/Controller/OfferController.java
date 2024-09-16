package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.Model.Offer;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.OfferService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offer")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffers();
        return ResponseEntity.status(200).body(offers);
    }
    // POST create a new offer
    @PostMapping("/create/{facilityRequestId}")
    public ResponseEntity createOffer(@AuthenticationPrincipal User user,
                                      @PathVariable Integer facilityRequestId,
                                      @Valid @RequestBody Offer offer) {
        offerService.createOffer(facilityRequestId, user.getId(), offer);
        return ResponseEntity.status(200).body(new ApiResponse("Offer Created Successfully"));
    }
    // PUT update an existing offer
    @PutMapping("/update/{offerId}")
    public ResponseEntity updateOffer(@AuthenticationPrincipal User user,
                                      @PathVariable Integer offerId,
                                      @Valid @RequestBody Offer updatedOffer) {
        offerService.updateOffer(offerId,user.getId(), updatedOffer);
        return ResponseEntity.status(200).body(new ApiResponse("Offer Updated Successfully"));
    }
    // PATCH cancel an offer
    @PutMapping("/cancel/{offerId}")
    public ResponseEntity cancelOffer(@AuthenticationPrincipal User user,
                                      @PathVariable Integer offerId) {
        offerService.cancelOffer(offerId, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Offer Canceled Successfully"));
    }
}