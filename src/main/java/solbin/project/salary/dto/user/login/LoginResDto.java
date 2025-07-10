package solbin.project.salary.dto.user.login;

import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.user.User;

/**
 *  LoginResDto - 로그인 응답 DTO
 */
@Getter @Setter
public class LoginResDto {
    private Long id;
    private String username;

    public LoginResDto(User user) {
        this.id = user.getId();
        this.username = user.getEmail();
    }
}
