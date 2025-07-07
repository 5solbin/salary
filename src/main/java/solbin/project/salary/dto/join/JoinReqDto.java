package solbin.project.salary.dto.join;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import solbin.project.salary.domain.User;

// check : validation 추가
@Getter @Setter
public class JoinReqDto {

    private String email;
    private String password;
    private String name;

    public User toEntity(BCryptPasswordEncoder encoder) {
        return User.builder()
                .email(email)
                .password(encoder.encode(password))
                .name(name)
                .build();
    }
}
