package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.User;
@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
    User findUserById(Integer id);
}
