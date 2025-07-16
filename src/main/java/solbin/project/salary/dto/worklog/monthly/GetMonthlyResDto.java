package solbin.project.salary.dto.worklog.monthly;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class GetMonthlyResDto {
    private String date;
    private Map<String, List<DailyInfo>> days;
    private Long totalTime;
    private Long salary;


    public GetMonthlyResDto(String date, Map<String, List<DailyInfo>> days, Long totalTime, Long salary) {
        this.date = date;
        this.days = days;
        this.salary = salary;
        this.totalTime = totalTime;
    }
}
