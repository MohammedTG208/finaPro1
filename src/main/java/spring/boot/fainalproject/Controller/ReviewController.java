package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.Model.Review;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.ReviewService;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/get-all")
    public ResponseEntity getAllReviews() {
        return ResponseEntity.status(200).body(reviewService.findAllReviewsByOrderId());
    }


    @PostMapping("/add-review/{orderId}")
    public ResponseEntity addReview(@PathVariable Integer orderId, @Valid @RequestBody Review review, @AuthenticationPrincipal User user) {
        reviewService.addReviewForProduct(orderId,review, user.getId());
        return ResponseEntity.status(200).body("review added successfully");
    }

    @PutMapping("/update/{reviewId}/{orderId}")
    public ResponseEntity updateReview(@PathVariable Integer orderId,@Valid @RequestBody Review review, @AuthenticationPrincipal User user, @PathVariable Integer reviewId) {
        reviewService.updateReviewForProduct(orderId,reviewId,review,user.getId());
        return ResponseEntity.status(200).body("update successful");
    }

    @DeleteMapping("/delete/{reviewId}/{orderId}")
    public ResponseEntity deleteReview(@PathVariable Integer orderId,@PathVariable Integer reviewId,@AuthenticationPrincipal User user) {
        reviewService.deleteReviewForProduct(orderId,reviewId,user.getId());
        return ResponseEntity.status(200).body("delete successful");
    }

    @GetMapping("/display-product-review")
    public ResponseEntity displayProductReview(@AuthenticationPrincipal User user,@RequestParam Integer orderId) {
        return ResponseEntity.status(200).body(reviewService.displayReviewForSupplier(orderId,user.getId()));
    }

}
