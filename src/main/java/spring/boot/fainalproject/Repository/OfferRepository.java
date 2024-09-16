package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.Offer;
@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer> {
    Offer findOfferById(Integer id);
}
