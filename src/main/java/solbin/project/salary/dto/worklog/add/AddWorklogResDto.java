package solbin.project.salary.dto.worklog.add;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.JobType;
import solbin.project.salary.domain.worklog.Worklog;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter @Setter
public class AddWorklogResDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
    private Long workTime;
    private Long userId;
    private Long salary;

    public AddWorklogResDto(Worklog workLog, JobType jobType) {
        this.endTime = workLog.getEndTime();
        this.startTime = workLog.getStartTime();
        this.workTime = Duration.between(startTime, endTime).toHours();
        this.userId = workLog.getUser().getId();
        this.salary = workTime*jobType.getPayRate();
    }
}
