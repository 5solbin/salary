package solbin.project.salary.dto.worklog.add;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.JobType;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.domain.worklog.Worklog;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class AddWorklogReqDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    private Long jobTypeId;

    public Worklog ToEntity(User user, JobType jobType) {

        int payRate = jobType.getPayRate().intValue();
        Long workTime = Duration.between(startTime, endTime).toHours();


        return Worklog.builder()
                .startTime(startTime)
                .endTime(endTime)
                .user(user)
                .jobType(jobType)
                .payRate(payRate)
                .totalWage(workTime.intValue() * payRate)
                .build();
    }
}
