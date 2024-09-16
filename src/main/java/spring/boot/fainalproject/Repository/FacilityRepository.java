package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.Facility;
@Repository
public interface FacilityRepository extends JpaRepository<Facility,Integer> {
    Facility findFacilityById(Integer id);
}
