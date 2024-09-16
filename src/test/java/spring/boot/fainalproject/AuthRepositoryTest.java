package spring.boot.fainalproject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        // Create a User instance
        user = new User();
        user.setUsername("Osama_S");
        user.setName("Osama Saeed");
        user.setPassword("password123");
        user.setRole("ADMIN");

        // Save the User entity
        authRepository.save(user);
    }

    @Test
    public void testFindUserByUsername() {
        // Test the findUserByUsername method
        User foundUser = authRepository.findUserByUsername("Osama_S");
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo("Osama_S");
        Assertions.assertThat(foundUser.getName()).isEqualTo("Osama Saeed");
        Assertions.assertThat(foundUser.getRole()).isEqualTo("ADMIN");
    }

    @Test
    public void testFindUserById() {
        // Test the findUserById method
        Optional<User> foundUser = authRepository.findById(user.getId());
        Assertions.assertThat(foundUser).isPresent();
        Assertions.assertThat(foundUser.get().getId()).isEqualTo(user.getId());
        Assertions.assertThat(foundUser.get().getUsername()).isEqualTo("Osama_S");
        Assertions.assertThat(foundUser.get().getRole()).isEqualTo("ADMIN");
    }
}
