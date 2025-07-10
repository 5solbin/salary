package solbin.project.salary.dto.worklog.add;

import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.domain.worklog.Worklog;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddReqDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Worklog ToEntity(User user) {
        return Worklog.builder()
                .startTime(startTime)
                .endTime(endTime)
                .user(user)
                .build();
    }
}
