package solbin.project.salary.dto.worklog.monthly;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class GetMonthlyResDto {
    private String date;
    private Map<String, DailyInfo> days;
    private Long totalTime;
    private Long salary;

    public GetMonthlyResDto(String date, Map<String, DailyInfo> days, Long totalTime) {
        this.totalTime = totalTime;
        this.date = date;
        this.days = days;
        this.salary = totalTime * 10000; // 시급은 고정 만원이라고 가정
    }
}
