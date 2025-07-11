package solbin.project.salary.dto.worklog.add;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.domain.worklog.Worklog;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddReqDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    public Worklog ToEntity(User user) {
        return Worklog.builder()
                .startTime(startTime)
                .endTime(endTime)
                .user(user)
                .build();
    }
}
