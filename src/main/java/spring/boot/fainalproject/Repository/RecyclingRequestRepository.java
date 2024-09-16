package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.RecyclingRequest;
@Repository
public interface RecyclingRequestRepository extends JpaRepository<RecyclingRequest, Integer> {
    RecyclingRequest findRecyclingRequestByid(Integer id);
}
