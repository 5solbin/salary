package solbin.project.salary.dto.worklog.update;

import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.worklog.Worklog;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter @Setter
public class UpdateResDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long workTime;
    private Long userId;

    public UpdateResDto(Worklog workLog) {
        this.endTime = workLog.getEndTime();
        this.startTime = workLog.getStartTime();
        this.workTime = Duration.between(startTime, endTime).toHours();
        this.userId = workLog.getUser().getId();
    }
}
