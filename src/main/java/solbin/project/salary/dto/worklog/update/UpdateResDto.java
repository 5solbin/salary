package solbin.project.salary.dto.worklog.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.worklog.Worklog;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter @Setter
public class UpdateResDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyyMMddHHmm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyyMMddHHmm", timezone = "Asia/Seoul")
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
