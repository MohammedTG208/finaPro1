package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.PriceOffer;
@Repository
public interface PriceOfferRepository extends JpaRepository<PriceOffer, Integer> {
    PriceOffer findPriceOfferById(Integer id);


}
