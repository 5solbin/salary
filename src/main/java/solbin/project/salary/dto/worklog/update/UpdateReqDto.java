package solbin.project.salary.dto.worklog.update;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UpdateReqDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;


}
