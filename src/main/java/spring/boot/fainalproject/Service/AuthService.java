package spring.boot.fainalproject.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<User> findAll() {
        return authRepository.findAll();
    }
    public void updateUser(User user, Integer id) {
        User user1 = authRepository.findUserById(id);
        if (user1 == null) {
            throw new ApiException("User not found");
        }
        user1.setUsername(user.getUsername());

        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user1.setPassword(hash);

        authRepository.save(user1);
    }

    public void deleteUser(Integer id) {
        User user1 = authRepository.findUserById(id);
        if (user1 == null) {
            throw new ApiException("User not found");
        }
        authRepository.delete(user1);
    }

    //extra 14
    public void deleteByAdmin(Integer adminId,Integer userId) {
        User admin = authRepository.findUserById(userId);
        if (admin.getRole().equals("ADMIN")){
            authRepository.deleteById(userId);
        }
    }
}
