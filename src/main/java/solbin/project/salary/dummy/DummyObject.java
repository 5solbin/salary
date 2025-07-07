package solbin.project.salary.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import solbin.project.salary.domain.User;

import java.time.LocalDateTime;

public class DummyObject {

    protected User newUser(String email, String name) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("1234");

        return User.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .build();
    }

    protected User newMockUser(Long id,String email, String name) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("1234");

        return User.builder()
                .id(id)
                .email(email)
                .password(encodedPassword)
                .name(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
