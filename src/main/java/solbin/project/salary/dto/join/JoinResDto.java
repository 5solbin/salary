package solbin.project.salary.dto.join;

import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.User;

@Getter @Setter
public class JoinResDto {

    private String name;
    private Long id;

    public JoinResDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
