package solbin.project.salary.dto.worklog.monthly;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter @Setter
public class DailyInfo {

    private String date;
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
    private Long workTime;

    public DailyInfo(String date, LocalDateTime startTime, LocalDateTime endTime, Long workTime) {
        this.date = date;
        this.endTime = endTime;
        this.startTime = startTime;
        this.workTime = Duration.between(startTime, endTime).toHours();
    }
}
