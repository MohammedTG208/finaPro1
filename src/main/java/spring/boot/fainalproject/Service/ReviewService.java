package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.Order;
import spring.boot.fainalproject.Model.Review;
import spring.boot.fainalproject.Repository.CustomerRepository;
import spring.boot.fainalproject.Repository.OrderRepository;
import spring.boot.fainalproject.Repository.ReviewRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public List<Review> findAllReviewsByOrderId() {
        return reviewRepository.findAll();
    }

    public void addReviewForProduct(Integer orderId, Review review, Integer userId) {
        Order customerOrder=orderRepository.findByOrderIdAndCustomerId(orderId, userId);
        Order facilityOrder=orderRepository.findOrderByIdAndFacilityId(orderId,userId);
        if (customerOrder!=null){
            review.setOrder(customerOrder);
            reviewRepository.save(review);
        }else if (facilityOrder!=null){
            review.setOrder(facilityOrder);
            reviewRepository.save(review);
        }else {
            throw new ApiException("Failed to add review");
        }
    }

    public void updateReviewForProduct(Integer orderId,Integer reviewId, Review review, Integer userId) {
        Order customerOrder=orderRepository.findByOrderIdAndCustomerId(orderId, userId);
        Order facilityOrder=orderRepository.findOrderByIdAndFacilityId(orderId,userId);
        if (customerOrder!=null) {
            Review oldReview = reviewRepository.findReviewById(reviewId);
            oldReview.setOrder(customerOrder);
            oldReview.setRate(review.getRate());
            oldReview.setDescription(review.getDescription());
            reviewRepository.save(oldReview);
        }else if (facilityOrder!=null) {
            Review oldReview = reviewRepository.findReviewById(reviewId);
            oldReview.setOrder(facilityOrder);
            oldReview.setRate(review.getRate());
            oldReview.setDescription(review.getDescription());
            reviewRepository.save(oldReview);
        }else {
            throw new ApiException("Failed to update review");
        }
    }


    public void deleteReviewForProduct(Integer orderId,Integer reviewId, Integer userId) {
        Order customerOrder=orderRepository.findByOrderIdAndCustomerId(orderId, userId);
        Order facilityOrder=orderRepository.findOrderByIdAndFacilityId(orderId,userId);
        if (customerOrder!=null) {
            reviewRepository.deleteReviewByIdAndOrderId(reviewId,customerOrder.getId());
        }else if (facilityOrder!=null) {
            reviewRepository.deleteReviewByIdAndOrderId(reviewId,facilityOrder.getId());
        }else {
            throw new ApiException("Failed to delete review");
        }
    }

    //what to add in github
    //وصف
    // رابط فقما رابط دوكمنت للpostman
    //presentation


    //display product review for supplier
    public Set<Review> displayReviewForSupplier(Integer orderId, Integer userId) {
        Order orderReview=orderRepository.findOrderForSupplier(userId, orderId);
        if (orderReview==null){
            throw new ApiException("Failed to find review");
        }
        if (!orderReview.getReviews().isEmpty()) {
            return  orderReview.getReviews();
        }else {
            throw new ApiException("You dont have any reviews for this product");
        }
    }
}
