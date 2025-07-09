package solbin.project.salary.dto.login;

import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.User;

@Getter @Setter
public class LoginResDto {
    private Long id;
    private String username;

    public LoginResDto(User user) {
        this.id = user.getId();
        this.username = user.getEmail();
    }
}
