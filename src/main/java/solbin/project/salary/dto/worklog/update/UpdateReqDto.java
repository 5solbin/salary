package solbin.project.salary.dto.worklog.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UpdateReqDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyyMMddHHmm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyyMMddHHmm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

}
